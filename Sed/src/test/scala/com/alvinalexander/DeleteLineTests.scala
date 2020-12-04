package tests

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import io.Source
import kaleidoscope._
import com.alvinalexander.sed.tostring._

/**
  * These tests demonstrate how to delete lines.
  */
class DeleteLineTests extends FunSuite with BeforeAndAfter {

    val headerIn = """---
    |partof: scala-tour
    |
    |num: 2
    |next-page: unified-types
    |previous-page: tour-of-scala
    |
    |redirect_from: "/tutorials/tour/basics.html"
    |---
    |""".stripMargin

    val headerOutExpected = """---
    |partof: scala-tour
    |
    |
    |redirect_from: "/tutorials/tour/basics.html"
    |---
    |""".stripMargin

    // NOTE: this method could be written like this, but i use
    //       the longer signature as a way to test it.
    // def deleteStuffInHeader(
    //     currentLine: String
    // ): SedAction = currentLine match {

    def deleteStuffInHeader(
        currentLine: String, 
        currentLineNum: Int,
        kvMap: Map[String, String]
    ): SedAction = currentLine match {
        case r"^num:.*"            => DeleteLine
        case r"^next-page:.*"      => DeleteLine
        case r"^previous-page:.*"  => DeleteLine
        case _                     => UpdateLine(currentLine)
    }

    test("delete stuff in a jekyll header (front matter section)") {
        val source = Source.fromString(headerIn)
        val sed: SedTrait = SedFactory.getSed(source, deleteStuffInHeader _)
        val sedResult = sed.run
        assert(sedResult == headerOutExpected)
        source.close
    }


}


