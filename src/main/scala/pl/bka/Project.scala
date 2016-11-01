package pl.bka

case class Project(name: String, log: List[String]) {
  override def toString = { name }
}
