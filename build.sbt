name := "worklogminer"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= List(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
  "com.github.nscala-time" %% "nscala-time" % "2.14.0",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test"
)