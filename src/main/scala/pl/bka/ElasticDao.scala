package pl.bka

import com.sksamuel.elastic4s.{ElasticClient, ElasticsearchClientUri, IndexResult}
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.source.Indexable
import pl.bka.model.db.ProjectDay
import spray.json._

import scala.concurrent.duration.Duration
import scala.concurrent.Await

class ElasticDao extends JsonProtocol {
  val uri = ElasticsearchClientUri("elasticsearch://localhost:9300")
  val client = ElasticClient.transport(uri)

  implicit object ProjectDayIndexable extends Indexable[ProjectDay] {
    override def json(t: ProjectDay): String = t.toJson.toString
  }

  def importData(dbData: Seq[ProjectDay]): Seq[IndexResult] = {
    Await.result(client.execute { create index "log"  }, Duration.Inf)

    dbData.map { projectDay =>
      //println(s"projectday ${ProjectDayIndexable.json(projectDay)}")
      Await.result(client.execute {
        index into "log" / "days" source projectDay
      }, Duration.Inf)
    }
  }

  def clearLogData() = {
    client.execute { deleteIndex("log") }
  }
}

