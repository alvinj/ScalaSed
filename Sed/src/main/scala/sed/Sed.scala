package com.alvinalexander.sed

import scala.io.Source

class Sed(filename: String, f:(String) => String) {

    val bufferedSource = Source.fromFile(filename)

    def run(): Unit = {
        for (currentLine <- bufferedSource.getLines) {
            val s = f(currentLine)
            println(s)
        }
        bufferedSource.close
    }

}

// object SedTest extends App {
//
//     def f(currentLine: String): String = currentLine match {
//         case r"^# ${header}@(.*)"  => s"<h1>$header</h1>"
//         case r"^## ${header}@(.*)" => s"<h2>$header</h2>"
//         case _                     => currentLine
//     }
//
//     val sed = new Sed("EXAMPLE.md", f)
//     sed.run
//
// }
