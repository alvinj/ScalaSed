# ScalaSed

It seems like I use the Unix/Linux `sed` command somewhere between one and five times every year, and when I need to use it, it’s often a struggle, made even worse by the fact that the MacOS `sed` version has its own quirks.

At one point I remember thinking, “I wish I could just do this easily with Scala,” and then I had that thought again, and again. Finally I broke down and took some time to create this “Scala Sed” project.


## Project organization

This project contains two sub-projects:

- The [Sed](Sed) project provides Unix/Linux `sed`-like capabilities. I don’t want to overstate/oversell it, it’s just an early first draft that I find useful. As of July, 2019, there are now two different Sed versions in that project, (a) one that prints to STDOUT and (b) another one that returns a string when it finishes processing the entire file. See that project for more details, tests, and examples.
- The [SedDemo](SedDemo) project demonstrates the earliest versions of the Sed class that prints to STDOUT. It’s slightly out of date because the Sed packages have been renamed, but they should work if the package names are updated.

If you want to use this project to print directly to STDOUT, see the SedDemo sub-project and the _com.alvinalexander.sed.stdout_ subdirectory in the Sed sub-project.

If you want to use this project to return a `String`, or if you want more advanced capabilities, see the _com.alvinalexander.sed.tostring_ package in the Sed sub-project. I used it more recently, so it currently has more capabilities than the STDOUT Sed version.

As a final note, I used to have more documentation here, but I’ve found that it’s easier to just keep the documentation in the subprojects, so please see the _Sed_ and _SedDemo_ subprojects for more information.

Alvin Alexander  
https://alvinalexander.com



