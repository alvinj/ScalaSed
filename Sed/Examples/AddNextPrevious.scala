/**
 * compile with this classpath:
 * .:sed_2.12-0.3.jar:kaleidoscope_2.12-0.1.0.jar
 */

import kaleidoscope._
import com.alvinalexander.sed.tostring._
import scala.io.Source
import java.io._
import java.nio.file.{Files, Paths, StandardCopyOption}

/**
 *  1 - read all filenames
 *  2 - delete old numeric value (`num: 57`)
 *  3 - go back thru files and add new numeric value
 *  4 - update the last file (`outof: 57`)
 */
object AddNextPrevious extends App {

    val filenames = Source.fromFile("LIST_OF_FILES_IN_ORDER")
        .getLines
        .filter(_.trim != "")
        .toList

    // loop through those filenames and replace/update everything
    var fileCounter = 1
    for (inputFile <- filenames) {

        // figure out prevPage and nextPage
        var prevPage = ""
        var nextPage = ""
        if (fileCounter == 1) {
            prevPage = ""
            nextPage = filenames(1)
        } 
        else if (fileCounter == filenames.size) {
            prevPage = filenames(fileCounter-2)
            nextPage = ""
        }
        else {
            prevPage = filenames(fileCounter-2)
            nextPage = filenames(fileCounter)
        }
        
        // rm the trailing ".scala" from the filenames
        prevPage = prevPage.split("\\.")(0)
        nextPage = nextPage.split("\\.")(0)
        
        val mapNeededBySedFunction = Map(
            "num" -> s"$fileCounter",
            "next-page" -> nextPage,
            "prev-page" -> prevPage
        )

        println(s"working on $inputFile ...")

        val source = Source.fromFile(inputFile)
        val sed: SedTrait = SedFactory.getSed(
            source,
            updateStuffInHeader _,
            mapNeededBySedFunction
        )
        val sedResult: String = sed.run

        // now have `newLines`. write the content back to the file.
        writeFile(inputFile, sedResult)

        // move the tmp file to the original filename
        fileCounter += 1
    }

    def updateStuffInHeader(
        currentLine: String, 
        currentLineNum: Int, 
        kvMap: Map[String, String]
    ): SedAction = {
        if (currentLine.startsWith("num:")) {
            // add the `next-page` and `prev-page` fields after the `num` field
            val nextPage = kvMap("next-page")
            val prevPage = kvMap("prev-page")
            val rez = s"${currentLine}\nprevious-page: ${prevPage}\nnext-page: ${nextPage}"
            return UpdateLine(rez)
        } else {
            return UpdateLine(currentLine)
        }
    }
    
    
    def writeFile(filename: String, s: String): Unit = {
        val file = new File(filename)
        val bw = new BufferedWriter(new FileWriter(file))
        bw.write(s)
        bw.close()
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



