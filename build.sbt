name := "worklogminer"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= List(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
  "com.github.nscala-time" %% "nscala-time" % "2.14.0",
  "com.sksamuel.elastic4s" %% "elastic4s-core" % "2.3.0",
  "com.sksamuel.elastic4s" %% "elastic4s-streams" % "2.3.0",
  "io.spray" %%  "spray-json" % "1.3.2",
  "org.slf4j" % "slf4j-simple" % "1.7.21",
  "com.typesafe.akka" %% "akka-stream" % "2.4.12",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)
