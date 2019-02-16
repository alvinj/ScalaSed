package com.alvinalexander.sed

import scala.io.Source

class MultilineSed(filename: String, f:(String, String) => Tuple2[Boolean, String]) {

    val bufferedSource = Source.fromFile(filename)

    def run(): Unit = {
        var prevLine = ""
        var pastFirstLine = false
        var prevLinesMerged = false

        for (currentLine <- bufferedSource.getLines) {
            val (functionMergedLines, s) = f(currentLine, prevLine)
            if (!pastFirstLine) {
                // for the first line just assign `s` to the prevLine
                prevLine = s
            }
            else {
                if (functionMergedLines) {
                    // `s` as a combination of the current line and the previous line,
                    // such as "Hi\n--" => "<h2>Hi</h2>"
                    println(s)
                    prevLinesMerged = true
                } else {
                    if (!prevLinesMerged) {
                        // always print the previous line, unless two lines were merged
                        println(prevLine)
                    }
                    prevLine = s
                    prevLinesMerged = false
                }
            }
            pastFirstLine = true
        }
        println(prevLine)

        bufferedSource.close
    }

}

// object MultilineSedTest extends App {
//
//     /**
//       * Return true if currentLine and prevLine were merged, false otherwise.
//       */
//     def f(currentLine: String, prevLine: String): (Boolean, String) = currentLine match {
//         case r"^\-+" => (true, s"<h2>$prevLine</h2>")
//         case r"^\=+" => (true, s"<h1>$prevLine</h1>")
//         case _       => (false, currentLine)
//     }
//
//     val sed = new MultilineSed("EXAMPLE.md", f)
//     sed.run
//
// }
