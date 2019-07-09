package com.alvinalexander.sed_tostring

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import io.Source
import kaleidoscope._

class SedToStringTests extends FunSuite with BeforeAndAfter {

    /*
     * https://qaftw.wordpress.com/2011/05/03/how-to-remove-empty-lines-from-text-in-java/
     * `?m` is a flag, equivalent of Java’s Pattern.MULTILINE.
     * It tells the regexp engine to match ^ and $ with beginnings 
     * and ends of lines instead of beginning and end of the whole 
     * text block. 
     */
    val s = """Four score and seven years ago,
    |our fathers did some stuff,
    |I don’t remember what exactly,
    |but it was important,
    |really important.
    |""".stripMargin

    //TODO there’s a minor bug here where i add a blank line at
    // the end of string
    val s1 = """Four score and seven years ago,
    |our fathers did some stuff,
    |I don’t remember what exactly,
    |but it was IMPORTANT,
    |really IMPORTANT.
    |""".stripMargin

    def basicSearchReplace(
        currentLine: String, 
        currentLineNum: Int,
        kvMap: Map[String, String]
    ): SedAction = currentLine match {
        // case r"^# ${header}@(.*)"  => s"<h1>$header</h1>"
        // case r"^## ${header}@(.*)" => s"<h2>$header</h2>"
        case r"$pre@(.*)important$post@(.*)" => UpdateLine(s"${pre}IMPORTANT${post}")
        case _                     => UpdateLine(currentLine)
    }

    test("basic search/replace") {
        val source = Source.fromString(s)
        val sed = new Sed(source, basicSearchReplace)
        val sResult = sed.run

        // println("===== START S1 ======")
        // println(s"'$s1'")
        // println("===== END S1 =====")

        // println("===== START sResult =====")
        // println(s"'$sResult'")
        // println("===== END sResult =====")

        assert(sResult == s1)
        source.close
    }
    
    

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
        val sed = new Sed(source, deleteStuffInHeader)
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
        val sed = new Sed(source, updateStuffInHeader, mapNeededBySedFunction)
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


