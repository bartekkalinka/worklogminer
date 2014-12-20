import com.github.nscala_time.time.Imports._

case class Project(name: String, log: List[String]) {
  override def toString = { name }
}

case class Workday(date: DateTime, projects: List[Project]) {
  override def toString = {
    DateTimeFormat.forPattern("dd.MM.yyyy").print(date) + " " +
      projects.map({ _.toString }).reduce(_ + ", " + _)
  }
}