package pl.bka.model.parse

import com.github.nscala_time.time.Imports._
import pl.bka.model.db.ProjectDay

case class Workday(date: DateTime, projects: List[Project]) {
  def dateString = DateTimeFormat.forPattern("dd.MM.yyyy").print(date)

  override def toString = {
    dateString + " " + projects.map({ _.toString }).reduce(_ + ", " + _)
  }

  def toProjectDays: Seq[ProjectDay] =
    projects.map { project => ProjectDay(date, project.name, project.log) }
}