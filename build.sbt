name := "worklogminer"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= List(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
  "com.github.nscala-time" %% "nscala-time" % "2.14.0",
  "com.sksamuel.elastic4s" %% "elastic4s-core" % "2.3.0",
  "io.spray" %%  "spray-json" % "1.3.2",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)