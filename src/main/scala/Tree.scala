import com.github.nscala_time.time.Imports._

case class Project(name: String, log: List[String])
case class Workday(date: DateTime, projects: List[Project])