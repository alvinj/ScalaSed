package sed_demo

import kaleidoscope._
import scala.io.Source
import com.alvinalexander.sed._

object MultilineSedTest extends App {

    /**
      * Return true if currentLine and prevLine were merged, false otherwise.
      */
    def f(currentLine: String, prevLine: String): (Boolean, String) = currentLine match {
        case r"^\-+" => (true, s"<h2>$prevLine</h2>")
        case r"^\=+" => (true, s"<h1>$prevLine</h1>")
        case _       => (false, currentLine)
    }

    val sed = new MultilineSed("/Users/al/Projects/Scala/Tests/Files/ScalaSed/EXAMPLE.md", f)
    sed.run

}
