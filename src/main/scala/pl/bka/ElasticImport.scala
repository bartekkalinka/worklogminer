package pl.bka

import com.sksamuel.elastic4s.{ElasticClient, ElasticsearchClientUri, IndexResult}
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.source.Indexable

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ElasticImport {

  // TODO use spray json
  implicit object WorkdayIndexable extends Indexable[Workday] {
    override def json(t: Workday): String =
      s""" { "date" : "${t.date}", "projects" : "[ ${t.projects.map(_.toJson).reduce(_ + ", " + _)} ]" } """
  }

  def importLogData(logData: List[Workday]): Future[List[IndexResult]] = {
    val uri = ElasticsearchClientUri("elasticsearch://localhost:9300")
    val client = ElasticClient.transport(uri)
    client.execute { create index "log"  }

    Future.traverse(logData) { workday =>
      client.execute {
        index into "log" / "workdays" source workday
      }
    }
  }
}

