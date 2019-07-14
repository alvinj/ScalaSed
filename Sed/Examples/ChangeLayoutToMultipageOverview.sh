#!/bin/sh
exec scala -nocompdaemon -savecompiled -classpath ".:sed_2.12-0.2.jar:kaleidoscope_2.12-0.1.0.jar" "$0" "$@"
!#

import kaleidoscope._
import com.alvinalexander.sed_tostring._
import scala.io.Source
import java.io._
import java.nio.file.{Files, Paths, StandardCopyOption}

val filenames = Source.fromFile("LIST_OF_FILES_IN_ORDER")
    .getLines
    .filter(_.trim != "")
    .toList

// loop through those filenames
for (inputFile <- filenames) {
    println(s"working on $inputFile ...")

    val source = Source.fromFile(inputFile)
    val sed = new Sed(source, updateStuffInHeader)
    val sedResult: String = sed.run

    writeFile(inputFile, sedResult)
}

def updateStuffInHeader(
    currentLine: String, 
    currentLineNum: Int, 
    kvMap: Map[String, String]
): SedAction = {
    if (currentLine.startsWith("layout:")) {
        return UpdateLine("layout: multipage-overview")
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


