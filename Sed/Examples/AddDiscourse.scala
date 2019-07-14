import kaleidoscope._
import com.alvinalexander.sed_tostring._
import scala.io.Source
import java.io._
import java.nio.file.{Files, Paths, StandardCopyOption}

object AddDiscourse extends App {

    val filenames = Source.fromFile("LIST_OF_FILES_IN_ORDER")
        .getLines
        .filter(_.trim != "")
        .toList

    // loop through those filenames and replace/update everything
    for (inputFile <- filenames) {
        println(s"working on $inputFile ...")

        val source = Source.fromFile(inputFile)
        val sed = new Sed(source, addDiscourseInHeader)
        val sedResult: String = sed.run

        writeFile(inputFile, sedResult)
    }

    def addDiscourseInHeader(
        currentLine: String, 
        currentLineNum: Int, 
        kvMap: Map[String, String]
    ): SedAction = {
        if (currentLine.startsWith("num:")) {
            // add the `discourse` line before `num`
            return UpdateLine(s"discourse: true\n${currentLine}")
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

}



