package com.alvinalexander.sed.tostring

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import io.Source
import kaleidoscope._

class SedToStringTests extends FunSuite with BeforeAndAfter {


    //////////////////////////////( Jekyll Header/Delete )//////////////////////////////

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

    test("delete stuff in jekyll header") {
        val source = Source.fromString(headerIn)
        val sed: SedTrait = SedFactory.getSed(source, deleteStuffInHeader _)
        val sedResult = sed.run

        // println("===== START headerOutExpected =====")
        // println(s"'$headerOutExpected'")
        // println("===== END headerOutExpected =====")
        // println("")
        // println("===== START actual result =====")
        // println(s"'$sedResult'")
        // println("===== END actual result =====")

        assert(sedResult == headerOutExpected)
        source.close
    }


    //////////////////////////////( Jekyll Header/Update )//////////////////////////////

    val headerOutExpectedAfterUpdate = """---
    |partof: scala-tour
    |
    |num: 42
    |next-page: FOO
    |previous-page: BAR
    |
    |redirect_from: "/tutorials/tour/basics.html"
    |---
    |""".stripMargin

    /**
     * this is a custom function for Sed that will update the desired fields
     * in the header of a Jekyll file.
     */
    def updateStuffInHeader(
        currentLine: String, 
        currentLineNum: Int, 
        kvMap: Map[String, String]
    ): SedAction = currentLine match {
        case r"^num:.*" => {
            val fileNumber = kvMap("num")
            UpdateLine(s"num: $fileNumber")
        }
        case r"^next-page:.*"     => {
            val nextPage = kvMap("next-page")
            UpdateLine(s"next-page: $nextPage")
        }
        case r"^previous-page:.*" => {
            val prevPage = kvMap("prev-page")
            UpdateLine(s"previous-page: $prevPage")
        }
        case _ => UpdateLine(currentLine)
    }

    test("update stuff in jekyll header") {
        val source = Source.fromString(headerIn)
        val mapNeededBySedFunction = Map(
            "num" -> "42",
            "next-page" -> "FOO",
            "prev-page" -> "BAR"
        )
        val sed: SedTrait = SedFactory.getSed(
            source, 
            updateStuffInHeader _, 
            mapNeededBySedFunction
        )
        val sedResult = sed.run

        // println("===== START headerOutExpectedAfterUpdate =====")
        // println(s"'$headerOutExpectedAfterUpdate'")
        // println("===== END headerOutExpectedAfterUpdate =====")
        // println("")
        // println("===== START actual result =====")
        // println(s"'$sedResult'")
        // println("===== END actual result =====")

        assert(sedResult == headerOutExpectedAfterUpdate)
        source.close
    }
}


