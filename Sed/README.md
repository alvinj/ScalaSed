# Sed (Scala)

July, 2019 Update: As I use this library more, I find that it needs more features to be useful. Therefore, please consider the API to be very unstable and likely to change. See the [README-DEV.md](README-DEV.md) file for additions made during this update.

As a brief note about these changes, everything under the *sed_tostring* directory is new. Please see the code in that directory — and the associated tests — to understand how this version of Sed works.

The first purpose of this new code was so that I could return a String from Sed rather than relying on Sed printing to STDOUT. The more I tried to use it for my needs, the more the other features were discovered, specifically the need to pass a key/value map to the custom function and the potential need for that function to know the current line number as Sed is parsing the input file. The key/value map contains values that you want to use in your custom sed function. See the tests for examples of what this means.


## Introduction

This is a little bit of a “sed” project written in Scala. The short story is that I was getting tired of trying to remember the Unix/Linux `sed` syntax when I was writing various scripts to modify files, so I took a little time to create this project so I can write sed-like scripts in Scala.

The code currently consists of two versions of this program under the _src/main/scala/sed_ directory:

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






