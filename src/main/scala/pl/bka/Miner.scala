package pl.bka

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Miner extends App {
  def parse(fileName: Option[String]): List[Workday] = {
    val input: String = io.Source.fromFile(fileName.getOrElse("input.txt")).mkString
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
      parse(None)
    case "import" =>
      val fileName = if(args.length >= 2) Some(args(1)) else None
      importData(parse(fileName))
  }
}