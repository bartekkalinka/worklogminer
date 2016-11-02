package pl.bka

import com.github.nscala_time.time.Imports._
import spray.json._

trait JsonProtocol extends DefaultJsonProtocol {
  implicit object MyDateTimeFormat extends RootJsonFormat[DateTime] {
    val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    def write(obj: DateTime): JsValue = JsString(formatter.print(obj))
    def read(json: JsValue): DateTime = json match {
      case JsString(s) => formatter.parseDateTime(s)
      case _ => throw new Exception("unparsable date")
    }
  }

  implicit val projectFormat = jsonFormat2(Project.apply)
  implicit val workdayFormat = jsonFormat2(Workday.apply)
}

