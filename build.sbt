name := "worklogminer"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies ++= List(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
  "com.github.nscala-time" %% "nscala-time" % "1.6.0",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test"
)

mainClass := Some("Miner")