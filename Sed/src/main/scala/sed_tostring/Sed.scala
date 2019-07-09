package com.alvinalexander.sed_tostring

import scala.io.Source

/**
 * I hope to add more documentation in the future, but for now,
 * call Sed as shown in the test case examples, specifically the
 * *SedToStringTests*.
 * 
 * @param bufferedSource For testing, `Source` can be `Scala.fromString`,
 * while in production it should be `Scala.fromFile`.
 * @param f The sed function, typically a `match` expression that details
 * the changes you want made.
 * @param keyValueMap A set of key/value pairs that you want to perform 
 * search and replace operations on.
 */
class Sed(
    bufferedSource: Source, 
    f:(String, Int, Map[String, String]) => SedAction,
    keyValueMap: Map[String, String] = Map("" -> "")
) {

    /**
     * Make the desired changes, then return the results as
     * a string when the complete Source has been processed.
     */
    def run(): String = {
        val sb = new StringBuilder
        var lineCount = 0
        for (currentLine <- bufferedSource.getLines) {
            lineCount += 1
            f(currentLine, lineCount, keyValueMap) match {
                case DeleteLine => // do nothing
                case UpdateLine(s) => sb.append(s + "\n")
            }
        }
        bufferedSource.close
        sb.toString
    }

}


/**
 * The possible actions that should be returned by your custom
 * function. Note that `UpdateLine` returns a String, so if you
 * want to prepend/append to the current line, you can return
 * a multiline string.
 */
sealed trait SedAction
case object DeleteLine extends SedAction
case class UpdateLine(s: String) extends SedAction


