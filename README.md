# ScalaSed

This project contains two sub-projects:

- The _Sed_ project provides Unix/Linux `sed`-like capabilities
  (I don’t want to overstate it, it’s a very early first draft
  that’s useful to me)
- The _SedDemo_ provides demonstrates how to use _Sed_

If this is your first time here you’ll probably want to look at
the SedDemo project first to see examples of how to use Sed.



## Brief introduction

As a very brief introduction, there are currently two
different Sed classes:

- `Sed` lets you operate on one line at a time
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




