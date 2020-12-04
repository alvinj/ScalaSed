# Sed (Scala)

This is a little bit of a “sed” project written in Scala. The short story is that I was getting tired of trying to remember the Unix/Linux `sed` syntax when I was writing various scripts to modify files, so I took a little time to create this project so I can write sed-like scripts in Scala.


## Update: December, 2020

Please see these classes for the latest code:

- [The Sed.scala class](src/main/scala/com/alvinalexander/sed/tostring/Sed.scala)
- [The tests in src/test/scala/com/alvinalexander](src/test/scala/com/alvinalexander)


## The latest code

Update: As I use this library more, I find that it needs more features to be useful. Therefore, please consider the API to be very unstable and likely to change.

As a brief note about the most recent changes, everything under the *sed.tostring* directory is the newest code. Please see the code in that directory — and the associated tests — to understand how this version of Sed works.

The first purpose of this new code is so that I can return a `String` from Sed rather than relying on Sed printing to STDOUT. After that, the more I tried to use it for my needs, the more the other features were discovered, specifically the need to pass a key/value map to the custom function and the potential need for that function to know the current line number as Sed is parsing the input file. The key/value map contains values that you want to use in your custom sed function. See the tests for examples of what this means.

>The most important thing to know at the moment is that the latest code is reflected in the tests in the *com.alvinalexander.sed.tostring.SedToStringTests* file and the code in the *com.alvinalexander.sed.tostring.Sed* file. Everything else is old, and will eventually be replaced.

>Any other code in this project that uses the older API is likely to go away in the future. At the moment, this includes the [SedDemo](SedDemo) code, which I haven’t updated yet.


## Introduction (previous version)

The code currently consists of two versions of this program under the _src/main/scala/com/alvinalexander/sed/stdout_ directory:

- `Sed` lets you work on one line at a time
- `MultilineSed` currently lets you merge/coalesce two lines
  into one, such as when you want to convert this:
  
````
Hello
-----
````

into this:

````
<h2>Hello</h2>
````

This is a first, barely-tested release of this code, so it
surely has bugs.



## Original experiments

The code under the _src/main/scala/original_ directory shows
the first few classes I wrote when I was originally experimenting
with this idea. I originally planned to peek ahead and the next
line to modify the current line, but somehow ended up using a
“buffer” approach, as shown in that code. (I wrote the code late
at night and in my tired stupor I forgot about the “peek” idea.)






