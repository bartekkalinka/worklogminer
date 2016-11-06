package pl.bka.model.parse

case class Project(name: String, log: List[String]) {
  override def toString = { name }
}
