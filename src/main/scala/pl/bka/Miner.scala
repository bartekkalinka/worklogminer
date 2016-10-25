package pl.bka

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Miner extends App {
  def parse: List[Workday] = {
    val input: String = io.Source.fromFile("input.txt").mkString
    val res: List[Workday] = LogParser(input)
    println(res.length)
    res.foreach { workday: Workday => println(workday) }
    res
  }

  def importData(logData: List[Workday]) = {
    Await.result(ElasticImport.importLogData(logData), Duration.Inf)
  }

  args(0) match {
    case "parse" =>
      parse
    case "import" =>
      importData(parse)
  }
}