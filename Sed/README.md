# Sed (Scala)

This is a little bit of a “sed” project written in Scala.
The short story is that I was getting tired of trying to
remember the `sed` syntax when I was writing various
scripts to modify files, so I took a little time to create
this project so I can write sed-like scripts in Scala.

The code currently consists of two versions of this program:

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
