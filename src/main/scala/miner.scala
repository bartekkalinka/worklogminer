import com.github.nscala_time.time.Imports._

import scala.util.parsing.combinator.RegexParsers

abstract class LogLine
case class SimpleLine(content: String) extends LogLine
case class DateLine(date: DateTime) extends LogLine
case class Workday(date: DateTime, log: List[String])

object LogParser extends RegexParsers {
  override def skipWhitespace = false
  def eol: Parser[String] = """\r\n""".r
  val dateRegex = """([0-9]{1,2})\.([0-9]{1,2})\.(20[0-9]{1,2})""".r
  def dateLine: Parser[DateLine] = dateRegex <~ eol ^^ {
    case dateRegex(d, m, y) => DateLine(new DateTime(y.toInt, m.toInt, d.toInt, 0, 0) )
  }
  def simpleLine: Parser[SimpleLine] = not(dateLine) ~> """.*""".r <~ eol ^^ { SimpleLine(_)}
  def workday: Parser[Workday] = dateLine ~ rep(simpleLine) ^^ {
    case ~(DateLine(date), simpleLines) => Workday(date, simpleLines map {case SimpleLine(sl) => sl})
  }
  def workdays: Parser[List[Workday]] = rep(workday)

  def apply(input: String): List[Workday] = {
    parseAll(workdays, input) match {
      case Success(result, _) => result
      case failure : NoSuccess => scala.sys.error(failure.msg)
    }
  }
}

object MinerApp extends App {
  val input: String = io.Source.fromFile("input.txt").mkString
  val res: List[Workday] = LogParser(input)
  println(res.length)
  res.foreach {
    case Workday(date, lines) =>
      println(DateTimeFormat.forPattern("dd.MM.yyyy").print(date) + " " + lines.length)
  }
}