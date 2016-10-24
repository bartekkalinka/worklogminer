package pl.bka

object Miner extends App {
  def parse: List[Workday] = {
    val input: String = io.Source.fromFile("input.txt").mkString
    val res: List[Workday] = LogParser(input)
    println(res.length)
    res.foreach { workday: Workday => println(workday) }
    res
  }

  args(0) match {
    case "parse" =>
      parse
  }
}