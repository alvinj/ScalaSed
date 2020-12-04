/**
 * compile with this classpath:
 * .:sed_2.12-0.4.jar:kaleidoscope_2.12-0.1.0.jar
 */

import kaleidoscope._
import com.alvinalexander.sed.tostring._
import scala.io.Source
import java.io._
import java.nio.file.{Files, Paths, StandardCopyOption}
import scala.io.BufferedSource

/**
  * This code assumes that *LIST_OF_FILES_IN_ORDER* and
  * all of the Markdown files are in the current directory.
  */
object UpdateAllMdFiles extends App {

    val filenames = Source.fromFile("LIST_OF_FILES_IN_ORDER")
        .getLines
        .filter(_.trim != "")
        .toList

    // loop through those filenames and replace `num`, 
    // `previous-page` and `next-page`
    var fileCounter = 1
    for (inputFile <- filenames) {

        println(s"updating $inputFile ...")

        val (prevPage, nextPage) = determinePrevNextPage(fileCounter, filenames)

        val source: BufferedSource = Source.fromFile(inputFile)

        // this gets the appropriate “Sed”, based on the 
        // number and type of parameters you pass in.
        val sed: SedTrait = SedFactory.getSed(
            source, 
            updateStuffInHeader _, 
            Map(
                "num" -> s"$fileCounter",
                "next-page" -> nextPage,
                "prev-page" -> prevPage
            )
        )

        // the `run` method in `Sed3Params` reads the file,
        // gets the current line, takes care of counting the lines,
        // and uses your `Map` to perform `DeleteLine` and
        // `UpdateLine` functions.
        val sedResult = sed.run

        // write the updated content back to the file
        writeFile(inputFile, sedResult)

        fileCounter += 1
    }

    // this function signature matches what SedFactory.getSed wants
    def updateStuffInHeader(
        currentLine: String, 
        currentLineNum: Int, 
        kvMap: Map[String, String]
    ): SedAction = currentLine match {
        case r"^num:.*" => {
            val fileNumber = kvMap("num")
            UpdateLine(s"num: $fileNumber")
        }
        case r"^next-page:.*"     => {
            val nextPage = kvMap("next-page")
            UpdateLine(s"next-page: $nextPage")
        }
        case r"^previous-page:.*" => {
            val prevPage = kvMap("prev-page")
            UpdateLine(s"previous-page: $prevPage")
        }
        case _ => UpdateLine(currentLine)
    }    

    // returns `(prevPage: String, nextPage: String)`
    def determinePrevNextPage(fileCounter: Int, filenames: Seq[String]): (String, String) = {
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
        (prevPage, nextPage)
    }

    def writeFile(filename: String, s: String): Unit = {
        val file = new File(filename)
        val bw = new BufferedWriter(new FileWriter(file))
        bw.write(s)
        bw.close()
    }

}



