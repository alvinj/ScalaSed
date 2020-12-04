package tests

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import io.Source
import kaleidoscope._
import com.alvinalexander.sed.tostring._

/**
  * These tests demonstrate how to update existing fields.
  */
class UpdateFieldsWithMapTests extends FunSuite with BeforeAndAfter {

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
    |num: 10
    |next-page: the-next-page
    |previous-page: the-previous-page
    |
    |redirect_from: "/tutorials/tour/basics.html"
    |---
    |""".stripMargin

    // these fields correspond to `headerOutExpected`
    val fileCounter = 10
    val nextPage = "the-next-page"
    val prevPage = "the-previous-page"

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

    test("update fields in a jekyll header (front matter section)") {
        val source = Source.fromString(headerIn)

        val sed: SedTrait = SedFactory.getSed(
            source, 
            updateStuffInHeader _, 
            Map(
                "num" -> s"$fileCounter",
                "next-page" -> nextPage,
                "prev-page" -> prevPage
            )
        )

        val sedResult = sed.run
        assert(sedResult == headerOutExpected)
        source.close
    }


}


