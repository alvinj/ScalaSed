#!/bin/sh
exec scala -nocompdaemon -savecompiled -classpath ".:sed_2.12-0.3.jar:kaleidoscope_2.12-0.1.0.jar:fileutils_2.12-0.1.jar" "$0" "$@"
!#

import kaleidoscope._
import com.alvinalexander.sed_tostring._
import com.alvinalexander.utils.FileUtils.readFileWithoutCommentsOrBlankLines
import com.alvinalexander.utils.FileUtils.writeFile
import scala.io.Source

val filenames = readFileWithoutCommentsOrBlankLines("LIST_OF_FILES_IN_ORDER")

for (inputFilename <- filenames) {
    println(s"processing $inputFilename ...")
    val source = Source.fromFile(inputFilename)
    val sedResult: String = SedFactory.getSed(source, updateLayout _).run
    writeFile(inputFilename, sedResult)
}

def updateLayout(currentLine: String): SedAction = {
    if (currentLine.startsWith("layout:")) {
        return UpdateLine("layout: book")
    } else {
        return UpdateLine(currentLine)
    }
}




