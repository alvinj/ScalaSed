name    := "ScalaSed"
version := "0.1"

// common settings
ThisBuild / scalaVersion := "2.12.8"

lazy val main = (project in file("."))
    .settings(
        name := "ScalaSed"
    )
    .aggregate(  //when you compile/package main, compile/package these others
        sed,
        sedDemo
    )

lazy val sed = (project in file("Sed"))
    .settings(
        name := "Sed"
    )

lazy val sedDemo = (project in file("SedDemo"))
    .settings(
        name := "SedDemo"
    )
    .dependsOn(  //put these on the classpath for the main project
        sed
    )

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

// see https://tpolecat.github.io/2017/04/25/scalac-flags.html for scalacOptions descriptions
scalacOptions ++= Seq(
    "-deprecation",     //emit warning and location for usages of deprecated APIs
    "-unchecked",       //enable additional warnings where generated code depends on assumptions
    "-explaintypes",    //explain type errors in more detail
    "-Ywarn-dead-code", //warn when dead code is identified
    "-Xfatal-warnings"  //fail the compilation if there are any warnings
)


