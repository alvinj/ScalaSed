package tests

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import io.Source
import kaleidoscope._
import com.alvinalexander.sed.tostring._

class PrependAppendTests extends FunSuite with BeforeAndAfter {

    /*
     * https://qaftw.wordpress.com/2011/05/03/how-to-remove-empty-lines-from-text-in-java/
     * `?m` is a flag, equivalent of Java’s Pattern.MULTILINE.
     * It tells the regexp engine to match ^ and $ with beginnings 
     * and ends of lines instead of beginning and end of the whole 
     * text block. 
     */
    val s = """Line 1
    |Line 3
    |Line 4
    |""".stripMargin

    //TODO there’s a minor bug here where i add a blank line at
    // the end of string
    val expected = """Line 1
    |Line 2
    |Line 3
    |Line 4
    |""".stripMargin


    // APPEND TEST //
    def appendLine2(
        currentLine: String
    ): SedAction = currentLine match {
        case "Line 1" => UpdateLine("Line 1\nLine 2")
        case _        => UpdateLine(currentLine)
    }

    test("append line 2") {
        val source = Source.fromString(s)
        val sed: SedTrait = SedFactory.getSed(source, appendLine2 _)
        val sedResult = sed.run
        assert(sedResult == expected)
        source.close
    }
    

    // PREPEND TEST //
    def prependLine2(
        currentLine: String
    ): SedAction = currentLine match {
        case "Line 3" => UpdateLine("Line 2\nLine 3")
        case _        => UpdateLine(currentLine)
    }

    test("prepend line 2") {
        val source = Source.fromString(s)
        val sed: SedTrait = SedFactory.getSed(source, appendLine2 _)
        val sedResult = sed.run
        assert(sedResult == expected)
        source.close
    }

}

