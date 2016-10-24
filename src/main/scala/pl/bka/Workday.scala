package pl.bka

import com.github.nscala_time.time.Imports._

case class Workday(date: DateTime, projects: List[Project]) {
  def dateString = DateTimeFormat.forPattern("dd.MM.yyyy").print(date)

  override def toString = {
    dateString + " " + projects.map({ _.toString }).reduce(_ + ", " + _)
  }
}