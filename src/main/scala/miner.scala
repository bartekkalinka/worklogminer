import com.github.nscala_time.time.Imports._

import scala.util.parsing.combinator.RegexParsers

object Miner extends App {
  def parse: List[Workday] = {
    val input: String = io.Source.fromFile("input.txt").mkString
    val res: List[Workday] = LogParser(input)
    println(res.length)
    res.foreach { workday: Workday => println(workday) }
    return res
  }

  args(0) match {
    case "parse" =>
      parse
    case "createDb" =>
      DbProxy.createSchema
    case "import" =>
      parse.foreach { workday => DbProxy.add(workday.dateString) }
  }
}