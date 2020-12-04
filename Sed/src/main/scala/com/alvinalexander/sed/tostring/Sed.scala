package com.alvinalexander.sed.tostring

import scala.io.Source

/**
 * These are the possible actions that can be returned by your custom
 * function. Note that `UpdateLine` returns a String, so if you
 * want to prepend/append to the current line, you can return
 * a multiline string.
 */
sealed trait SedAction
case object DeleteLine extends SedAction
case class UpdateLine(s: String) extends SedAction

/**
 * I hope to add more documentation in the future, but for now,
 * call Sed as shown in the test case examples, specifically the
 * `SedToStringTests`.
 * 
 * @param source For testing, `Source` can be `Scala.fromString`,
 * while in production it should be `Scala.fromFile`.
 * @param f Your “sed” function, typically a `match` expression that details
 * the changes you want made.
 * @param keyValueMap A set of key/value pairs that you want to perform 
 * search and replace operations on.
 */

trait SedTrait {
    def run(): String
}

/**
  * This “factory” relies on the fact that the functions that are
  * passed in are of type `Function3`, `Function2`, and `Function1`.
  */
object SedFactory {

    // the function parameters are: currentLine, currentLineNum, map
    def getSed(
        source: Source,
        f:(String, Int, Map[String, String]) => SedAction,
        keyValueMap: Map[String, String] = Map("" -> "")
    ): SedTrait = {
        new Sed3Params(source, f, keyValueMap)
    }

    // the function parameters are: currentLine, currentLineNum
    def getSed(
        source: Source, 
        f:(String, Int) => SedAction
    ): SedTrait = {
        new SedCurrentLineAndNum(source, f)
    }

    // the function parameter is: currentLine
    def getSed(
        source: Source, 
        f:(String) => SedAction
    ): SedTrait = {
        new SedCurrentLine(source, f)
    }

    /**
     * Simple version, only uses the current line, no line count or map.
     */
    private class SedCurrentLine(source: Source, f:(String) => SedAction) extends SedTrait
    {
        def run(): String = {
            val sb = new StringBuilder
            for (currentLine <- source.getLines) {
                f(currentLine) match {
                    case DeleteLine => // do nothing
                    case UpdateLine(s) => sb.append(s + "\n")
                }
            }
            source.close
            sb.toString
        }
    }

    /**
     * Uses the current line and current line number.
     */
    private class SedCurrentLineAndNum(source: Source, f:(String, Int) => SedAction) extends SedTrait
    {
        def run(): String = {
            val sb = new StringBuilder
            var lineCount = 0
            for (currentLine <- source.getLines) {
                lineCount += 1
                f(currentLine, lineCount) match {
                    case DeleteLine => // do nothing
                    case UpdateLine(s) => sb.append(s + "\n")
                }
            }
            source.close
            sb.toString
        }
    }

    /**
     * Uses the current line, current line number, and a map of substitution values.
     */
    private class Sed3Params(
        source: Source, 
        f:(String, Int, Map[String, String]) => SedAction,
        keyValueMap: Map[String, String] = Map("" -> "")
    ) extends SedTrait
    {
        def run(): String = {
            val sb = new StringBuilder
            var lineCount = 0
            for (currentLine <- source.getLines) {
                lineCount += 1
                f(currentLine, lineCount, keyValueMap) match {
                    case DeleteLine => // do nothing
                    case UpdateLine(s) => sb.append(s + "\n")
                }
            }
            source.close
            sb.toString
        }
    }

}  //SedFactory

