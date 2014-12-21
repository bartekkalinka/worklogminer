import scala.slick.driver.PostgresDriver.simple._

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
      workdays.ddl.create
    }
  }
}