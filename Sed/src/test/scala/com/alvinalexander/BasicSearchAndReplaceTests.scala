package tests

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import io.Source
import kaleidoscope._
import com.alvinalexander.sed.tostring._

class BasicSearchAndReplaceTests extends FunSuite with BeforeAndAfter {

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
        currentLine: String
    ): SedAction = currentLine match {
        // case r"^# ${header}@(.*)"  => s"<h1>$header</h1>"
        // case r"^## ${header}@(.*)" => s"<h2>$header</h2>"
        case r"$pre@(.*)important$post@(.*)" => UpdateLine(s"${pre}IMPORTANT${post}")
        case _                     => UpdateLine(currentLine)
    }

    test("basic search/replace") {
        val source = Source.fromString(s)
        val sed: SedTrait = SedFactory.getSed(source, basicSearchReplace _)
        val sResult = sed.run
        assert(sResult == s1)
        source.close
    }
    

}


