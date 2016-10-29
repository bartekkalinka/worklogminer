package pl.bka

import com.github.nscala_time.time.Imports._
import org.scalatest._

class LogParserSuite extends FunSuite {

  val input: String =
    """27.08.2012
      |24:15
      |- worklogminer
      |    - aaa
      |    - bbb
      |        - ccc
      |- other stuff
      |    - aaa
      |        - bbb
      |
      |24.08.2012
      |- some minor activity
      |    - ccc
      |- worklogminer
      |    - aaa
      |    - bbb
      |
      |""".stripMargin

  test("Parses days with dates") {
    val result = LogParser(input)
    assert(result.length == 2)
    val day1: Workday = result(0)
    assert(day1.date == new DateTime(2012, 8, 27, 0, 0))
    val day2: Workday = result(1)
    assert(day2.date == new DateTime(2012, 8, 24, 0, 0))
  }

  //TODO test projects
}
