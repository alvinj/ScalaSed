# Sed Demo

This project demonstrates how to use the Sed classes.



## Sed example

Here’s a little example of how to use the `Sed` class:

```scala
object SedTest extends App {

    def updateLine(currentLine: String): String = currentLine match {
        case r"^# ${header}@(.*)"  => s"<h1>$header</h1>"
        case r"^## ${header}@(.*)" => s"<h2>$header</h2>"
        case _                     => currentLine
    }

    val sed = new Sed(
        "/Users/al/Projects/ScalaSed/EXAMPLE.md", 
        updateLine
    )
    sed.run

}
```

As that code shows, to use `Sed` you just need to define a function that has this type:

- `String => String`

When you call the Sed’s `run` method, your function is called with each line that Sed reads from the file you supply, so your function should do whatever you want for any/every pattern you’re looking for. If you just want a line to pass through without any changes, just do what’s shown in the default match case.



## Using in a shell script

My plan is to use Sed in a shell script, so here’s what that same code looks like as a (precompiled) Scala shell script:

````
#!/bin/sh
exec scala -classpath ".:sed_2.12-0.1.jar:kaleidoscope_2.12-0.1.0.jar" -savecompiled "$0" "$@"
!#

import kaleidoscope._
import scala.io.Source
import com.alvinalexander.sed._

def f(currentLine: String): String = currentLine match {
    case r"^# ${header}@(.*)"  => s"<h1>$header</h1>"
    case r"^## ${header}@(.*)" => s"<h2>$header</h2>"
    case _                     => currentLine
}

val sed = new Sed("/Users/al/Projects/ScalaSed/EXAMPLE.md", f)
sed.run
````

This source code comes from the _sedDemo.sh_ file in the _ScriptExample_ directory.



## MultilineSed

There’s also a class named `MultilineSed` that lets you make updates to the previous line depending on what you find on the current line. That’s useful when you want to transform two lines like this:

````
Hello
-----
````

into one output line like this:

````
<h2>Hello</h2>
````

I’ll add documentation for `MultilineSed` in the future, but for now you can look at [MultilineSed.scala](https://github.com/alvinj/ScalaSed/blob/master/SedDemo/src/main/scala/sed/MultilineSedTest.scala) to get a hint at how it currently works.

(This class also needs a better name. It’s more like “Two-line Sed” than “MultilineSed.”)



## Dependencies

This project uses Jon Pretty’s [Kaleidoscope](https://github.com/propensive/kaleidoscope) project, which enables pattern-matching in Scala `match` expressions.


