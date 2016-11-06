package pl.bka

import com.github.nscala_time.time.Imports._
import org.joda.time.format.ISODateTimeFormat
import pl.bka.model.db.ProjectDay
import spray.json._

trait JsonProtocol extends DefaultJsonProtocol {
  implicit object MyDateTimeFormat extends RootJsonFormat[DateTime] {
    val formatter = ISODateTimeFormat.dateHourMinuteSecondMillis
    def write(obj: DateTime): JsValue = JsString(formatter.print(obj))
    def read(json: JsValue): DateTime = json match {
      case JsString(s) => formatter.parseDateTime(s)
      case _ => throw new Exception("unparsable date")
    }
  }

  implicit val projectDayFormat = jsonFormat3(ProjectDay.apply)
}

