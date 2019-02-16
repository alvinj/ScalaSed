package sed_demo

import kaleidoscope._
import scala.io.Source
import com.alvinalexander.sed._

object SedTest extends App {

    def f(currentLine: String): String = currentLine match {
        case r"^# ${header}@(.*)"  => s"<h1>$header</h1>"
        case r"^## ${header}@(.*)" => s"<h2>$header</h2>"
        case _                     => currentLine
    }

    val sed = new Sed("/Users/al/Projects/Scala/Tests/Files/ScalaSed/EXAMPLE.md", f)
    sed.run

}
