import com.github.nscala_time.time.Imports._

import scala.util.parsing.combinator.RegexParsers

case class Project(name: String, log: List[String])
case class Workday(date: DateTime, projects: List[Project])

object LogParser extends RegexParsers {
  override def skipWhitespace = false
  var lineNo: Int = 0
  def eol: Parser[String] = { lineNo = lineNo + 1; """\r\n""".r }
  val dateRegex = """([0-9]{1,2})\.([0-9]{1,2})\.(20[0-9]{1,2})""".r
  def dateLine: Parser[DateTime] = dateRegex <~ eol ^^ {
    case dateRegex(d, m, y) => new DateTime(y.toInt, m.toInt, d.toInt, 0, 0)
  }
  val anything = """.*""".r
  def projectLine: Parser[String] = "- " ~> anything <~ eol
  def simpleLine: Parser[String] = not(dateLine | projectLine) ~> anything <~ eol
  def project: Parser[Project] = projectLine ~ rep(simpleLine) ^^ {
    case ~(name, simpleLines) => Project(name, simpleLines)
  }
  def workday: Parser[Workday] = dateLine ~ opt(simpleLine) ~ rep(project) ^^ {
    case ~(~(date, lineOpt), projects) => println(date); Workday(date, projects)
  }
  def workdays: Parser[List[Workday]] = rep(workday)

  def apply(input: String): List[Workday] = {
    parseAll(workdays, input) match {
      case Success(result, _) => result
      case failure : NoSuccess => scala.sys.error("line " + lineNo + " " + failure.msg)
    }
  }
}

object MinerApp extends App {
  val input: String = io.Source.fromFile("input.txt").mkString
  val res: List[Workday] = LogParser(input)
  println(res.length)
  res.foreach {
    case Workday(date, projects) =>
      println(DateTimeFormat.forPattern("dd.MM.yyyy").print(date) + " " +
        projects.map({ case Project(name, _) => name }).reduce(_ + ", " + _) )
  }
}