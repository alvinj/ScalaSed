
import scala.io.Source
import java.io._
import java.nio.file.{Files, Paths, StandardCopyOption}

object RemoveAllH1Tags extends App {

    val filenames = Source.fromFile("LIST_OF_FILES_IN_ORDER")
        .getLines
        .filter(_.trim != "")
        .toList

    // loop through every file and remove lines beginning with `# `
    for (inputFile <- filenames) {

        println(s"working on $inputFile ...")

        // create a temp filename
        val tmpFilename = inputFile + ".tmp"

        // replace the `num:` line from the inputFile
        val newLines = removeH1Lines(inputFile)

        // now have `newLines`. write the content back to the file.
        writeFile(inputFile, newLines)

    }

    def removeH1Lines(filename: String): List[String] = {
        val bufferedSource = Source.fromFile(filename)
        val newLines: List[String] = (for (currentLine <- bufferedSource.getLines) yield {
            if (currentLine.startsWith("# ")) {
                ""
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



