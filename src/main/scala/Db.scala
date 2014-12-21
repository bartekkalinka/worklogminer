import scala.slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.meta.MTable

class Workdays(tag: Tag) extends Table[(Int, String)](tag, "WORKDAYS") {
  def id = column[Int]("ID", O.PrimaryKey)
  def name = column[String]("NAME")
  def * = (id, name)
}

object DbProxy {
  def createSchema = {
    val workdays: TableQuery[Workdays] = TableQuery[Workdays]
    val db = Database.forURL("jdbc:postgresql://localhost/postgres",
      driver = "org.postgresql.Driver", user = "postgres", password = "postgres")
    db.withSession { implicit session =>
      if(!MTable.getTables(None, Some("public"), None, None).list.map({ _.name.name }).exists( _ == "WORKDAYS")) {
        workdays.ddl.create
      }
    }
  }
}