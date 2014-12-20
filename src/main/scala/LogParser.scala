import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime

import scala.util.parsing.combinator.RegexParsers

object LogParser extends RegexParsers {
  override def skipWhitespace = false
  def eol: Parser[String] = sys.props("line.separator")
  val dateRegex = """([0-9]{1,2})\.([0-9]{1,2})\.(20[0-9]{1,2}).*""".r
  def dateLine: Parser[DateTime] = dateRegex <~ eol ^^ {
    case dateRegex(d, m, y) => new DateTime(y.toInt, m.toInt, d.toInt, 0, 0)
  }
  val anything = """.*""".r
  def projectLine: Parser[String] = "- " ~> anything <~ eol
  def simpleLine: Parser[String] = not(dateLine | projectLine) ~> anything <~ eol
  def project: Parser[Project] = projectLine ~ rep(simpleLine) ^^ {
    case ~(name, simpleLines) => Project(name, simpleLines)
  }
  def workday: Parser[Workday] = dateLine ~ rep(simpleLine) ~ rep(project) ^^ {
    case ~(~(date, _), projects) => Workday(date, projects)
  }
  def workdays: Parser[List[Workday]] = rep(workday)

  def apply(input: String): List[Workday] = {
    parseAll(workdays, input) match {
      case Success(result, _) => result
      case failure : NoSuccess => scala.sys.error("line " + failure.next.pos.line + " " + failure.msg)
    }
  }
}