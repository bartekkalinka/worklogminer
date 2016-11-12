package pl.bka

import pl.bka.model.db.ProjectDay
import pl.bka.model.parse.Workday

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
    val elasticDao = new ElasticDao
    val dbData: List[ProjectDay] = logData.flatMap(_.toProjectDays)
    elasticDao.importData(dbData)
  }

  val fileName = if(args.length >= 2) Some(args(1)) else None

  args(0) match {
    case "parse" =>
      parse(fileName)
    case "import" =>
      importData(parse(fileName))
    case "clear" =>
      val elasticDao = new ElasticDao
      Await.result(elasticDao.clearLogData(), Duration.Inf)
  }
}