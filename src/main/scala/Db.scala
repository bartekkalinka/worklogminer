import scala.slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.meta.MTable

class Workdays(tag: Tag) extends Table[(Int, String)](tag, "WORKDAYS") {
  def id = column[Int]("ID", O.PrimaryKey)
  def workDate = column[String]("WORKDATE")
  def * = (id, workDate)
}

object DbProxy {
  var seq: Int = 1
  val workdays: TableQuery[Workdays] = TableQuery[Workdays]
  val db = Database.forURL("jdbc:postgresql://localhost/postgres",
    driver = "org.postgresql.Driver", user = "postgres", password = "postgres")

  def createSchema = {
    db.withSession { implicit session =>
      if(!MTable.getTables(None, Some("public"), None, None).list.map({ _.name.name }).exists( _ == "WORKDAYS")) {
        workdays.ddl.create
      }
    }
  }

  def add(date: String) = {
    db.withSession { implicit session =>
      workdays +=(seq, date)
    }
    seq += 1
  }
}