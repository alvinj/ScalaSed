package original

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object FilePeeker1 extends App {

    val bufferedSource = Source.fromFile("README.md")

    val linesOut = ArrayBuffer[String]()
    var count = 0
    for (line <- bufferedSource.getLines) {
        if (line.matches("^\\-+$")) { //a line like "------"
            val prevLine = linesOut(count-1)
            println(prevLine)
            linesOut(count-1) = s"<h2>$prevLine</h2>"
        }
        else if (line.matches("^\\=+$")) { //a line like "======"
            val prevLine = linesOut(count-1)
            linesOut(count-1) = s"<h1>$prevLine</h1>"
        }
        else {
            linesOut.append(line)
            count += 1
        }
    }

    linesOut.foreach(println)

    bufferedSource.close

}
