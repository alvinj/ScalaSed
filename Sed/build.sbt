name := "Sed"
version := "0.3"

// this is a kludge to let me create a package (the main methods
// don’t matter for packaging)
mainClass in (Compile, packageBin) := Some("original.FileLookAhead")

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % "test",
    "com.propensive" %% "kaleidoscope" % "0.1.0" % "test"
)

//see https://tpolecat.github.io/2017/04/25/scalac-flags.html for scalacOptions descriptions
scalacOptions ++= Seq(
    "-deprecation",     //emit warning and location for usages of deprecated APIs
    "-unchecked",       //enable additional warnings where generated code depends on assumptions
    "-explaintypes",    //explain type errors in more detail
    "-Ywarn-dead-code", //warn when dead code is identified
    "-Xfatal-warnings"  //fail the compilation if there are any warnings
)

