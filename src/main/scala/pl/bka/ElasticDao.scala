package pl.bka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import com.sksamuel.elastic4s.{BulkCompatibleDefinition, ElasticClient, ElasticsearchClientUri, IndexResult}
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.source.Indexable
import pl.bka.model.db.ProjectDay
import spray.json._
import com.sksamuel.elastic4s.streams.ReactiveElastic._
import com.sksamuel.elastic4s.streams.RequestBuilder

class ElasticDao extends JsonProtocol {
  val uri = ElasticsearchClientUri("elasticsearch://localhost:9300")
  val client = ElasticClient.transport(uri)

  implicit object ProjectDayIndexable extends Indexable[ProjectDay] {
    override def json(t: ProjectDay): String = t.toJson.toString
  }

  def importData(dbData: List[ProjectDay]) = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val source = Source[ProjectDay](dbData)

    implicit val builder = new RequestBuilder[ProjectDay] {
      import com.sksamuel.elastic4s.ElasticDsl._
      def request(projectDay: ProjectDay): BulkCompatibleDefinition =  index into "log" / "days" source projectDay
    }
    val subscriber = client.subscriber[ProjectDay](batchSize = 100, concurrentRequests = 1)
    val sink = Sink.fromSubscriber(subscriber)

    source.to(sink).run()
  }

  def clearLogData() = {
    client.execute { deleteIndex("log") }
  }
}

