/**
 * compile with this classpath:
 * .:sed_2.12-0.1.jar:kaleidoscope_2.12-0.1.0.jar
 */

import kaleidoscope._
import com.alvinalexander.sed_tostring._
import scala.io.Source
import java.io._
import java.nio.file.{Files, Paths, StandardCopyOption}

/**
 * Delete the 'next-page' and 'previous-page' fields in all *.md files.
 * This script seemed to work well.
 */
object DeleteNextPrevFields extends App {

    val filenames = Source.fromFile("LIST_OF_FILES_IN_ORDER")
        .getLines
        .filter(_.trim != "")
        .toList

    // loop through every file
    for (inputFile <- filenames) {
        println(s"working on $inputFile ...")

        val source = Source.fromFile(inputFile)
        val sed = new Sed(source, updateStuffInHeader)
        val sedResult: String = sed.run

        // now have `newLines`. write the content back to the file.
        writeFile(inputFile, sedResult)
    }

    def updateStuffInHeader(
        currentLine: String, 
        currentLineNum: Int, 
        kvMap: Map[String, String]
    ): SedAction = currentLine match {
        case r"^next-page:.*"     => DeleteLine
        case r"^previous-page:.*" => DeleteLine
        case _ => UpdateLine(currentLine)
    }
    
    def writeFile(filename: String, lines: Seq[String]): Unit = {
        val file = new File(filename)
        val bw = new BufferedWriter(new FileWriter(file))
        for (line <- lines) {
            bw.write(line)
        }
        bw.close()
    }

    def writeFile(filename: String, s: String): Unit = {
        val file = new File(filename)
        val bw = new BufferedWriter(new FileWriter(file))
        bw.write(s)
        bw.close()
    }

}



