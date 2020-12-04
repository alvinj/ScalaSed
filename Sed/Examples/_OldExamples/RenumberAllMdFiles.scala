/**
 * compile with this classpath:
 * .:sed_2.12-0.1.jar:kaleidoscope_2.12-0.1.0.jar
 */

import kaleidoscope._
import com.alvinalexander.sed._
import scala.io.Source
import java.io._
import java.nio.file.{Files, Paths, StandardCopyOption}

/**
 *  1 - read all filenames
 *  2 - delete old numeric value (`num: 57`)
 *  3 - go back thru files and add new numeric value
 *  4 - update the last file (`outof: 57`)
 */
object RenumberAllMdFiles extends App {

    val filenames = Source.fromFile("LIST_OF_FILES_IN_ORDER")
        .getLines
        .filter(_.trim != "")
        .toList

    // loop through those filenames and replace the old `num: xx` with
    // a new counter
    var fileCounter = 1
    for (inputFile <- filenames) {

        println(s"working on $inputFile ...")

        // create a temp filename
        val tmpFilename = inputFile + ".tmp"

        // replace the `num:` line from the inputFile
        val newLines = replaceNumAndOutof(inputFile)

        // now have `newLines`. write the content back to the file.
        writeFile(inputFile, newLines)

        // move the tmp file to the original filename
        fileCounter += 1
    }

    def replaceNumAndOutof(filename: String): List[String] = {
        val bufferedSource = Source.fromFile(filename)
        val newLines: List[String] = (for (currentLine <- bufferedSource.getLines) yield {
            if (currentLine.startsWith("num:")) {
                s"num: $fileCounter\n"
            }
            else if (currentLine.startsWith("outof:")) {
                s"outof: $fileCounter\n"
            }
            else {
                s"$currentLine\n"
            }
        }).toList
        bufferedSource.close
        newLines
    }

    def writeFile(filename: String, lines: Seq[String]): Unit = {
        val file = new File(filename)
        val bw = new BufferedWriter(new FileWriter(file))
        for (line <- lines) {
            bw.write(line)
        }
        bw.close()
    }

}



