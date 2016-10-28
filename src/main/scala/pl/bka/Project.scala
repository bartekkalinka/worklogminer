package pl.bka

case class Project(name: String, log: List[String]) {
  override def toString = { name }

  def toJson: String = {
    def jsonLine(line: String) = s"""{ "line": "$line" }"""
    s"""{ "name" : "$name", "log": [ ${log.map(jsonLine).reduce(_ + ", " + _)} ] } """
  }
}
