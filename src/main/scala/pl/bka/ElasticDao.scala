package pl.bka

import com.sksamuel.elastic4s._
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.source.Indexable
import pl.bka.model.db.ProjectDay
import spray.json._

import scala.concurrent.Future

class ElasticDao extends JsonProtocol {
  val uri = ElasticsearchClientUri("elasticsearch://localhost:9300")
  val client = ElasticClient.transport(uri)

  implicit object ProjectDayIndexable extends Indexable[ProjectDay] {
    override def json(t: ProjectDay): String = t.toJson.toString
  }

  def importData(dbData: Seq[ProjectDay]): Future[BulkResult] = {
    client.execute { create index "log"  }

    client.execute {
      bulk(dbData.map(index into "log" / "days" source _))
    }
  }

  def clearLogData() = {
    client.execute { deleteIndex("log") }
  }
}

