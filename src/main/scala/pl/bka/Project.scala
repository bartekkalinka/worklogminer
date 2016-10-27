package pl.bka

case class Project(name: String, log: List[String]) {
  override def toString = { name }
  def toJson: String = s"""{ "name" : "$name", "log": "${log.reduce(_ + " [newline] " + _ )}" } """
}
