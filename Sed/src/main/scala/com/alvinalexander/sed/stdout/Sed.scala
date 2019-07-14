package com.alvinalexander.sed.stdout

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
