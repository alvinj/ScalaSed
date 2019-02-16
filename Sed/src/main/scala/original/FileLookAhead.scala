package original

import scala.io.Source

/**
  * The purpose of this code is to find a little Scala
  * file-processing algorithm that works like sed,
  * except I also want the ability to modify the previous
  * line of text based on the current line. As shown in
  * the code, this lets me use "---" on the current line
  * to convert the previous line into an H2 tag. This is
  * just one example of a file-processing pattern that
  * I run into several times a year.
  *
  * Note to self: I tested this on a file with ~10M lines
  * and memory use did not increase. `bufferedSource.getLines`
  * is an iterator.
  */
object FileLookAhead extends App {

    val bufferedSource = Source.fromFile("README.md")

    var buffer = ""
    var pastFirstLine = false

    for (line <- bufferedSource.getLines) {
        if (line.matches("^\\-+$")) {      //a line like "-----"
            buffer = s"<h2>$buffer</h2>"
        }
        else if (line.matches("^\\=+$")) { //a line like "====="
            buffer = s"<h1>$buffer</h1>"
        }
        else {
            if (pastFirstLine) println(buffer)
            buffer = line
        }
        pastFirstLine = true
    }
    println(buffer)

    bufferedSource.close

}
