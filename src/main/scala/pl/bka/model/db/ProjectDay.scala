package pl.bka.model.db

import com.github.nscala_time.time.Imports._

case class ProjectDay(date: DateTime, name: String, log: List[String])
