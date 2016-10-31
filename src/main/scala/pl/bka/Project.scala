package pl.bka

case class Project(name: String, log: List[String]) {
  override def toString = { name }

  def toJson: String = {
    def jsonLine(line: String) = s"""{ "line": "$line" }"""
    val linesJsonArr = if(log.isEmpty) "" else log.map(jsonLine).reduce(_ + ", " + _)
    s"""{ "name" : "$name", "log": [ $linesJsonArr ] } """
  }
}
