package com.datamountaineer.streamreactor.socketstreamer

import akka.actor.ActorSystem
import akka.http.scaladsl.testkit.{ScalatestRouteTest, WSProbe}
import com.datamountaineer.streamreactor.socketstreamer.routes.SocketRoutes
import org.scalatest._

/**
  * Created by andrew@datamountaineer.com on 16/03/16. 
  * stream-reactor-websocket-feeder
  */
class RouteTest extends FlatSpec with Matchers with ScalatestRouteTest with SocketRoutes {
  it should "handle websocket requests for topics" in {
    implicit val system = ActorSystem()
    val wsClient = WSProbe()
    WS("/ws/topics?topic=test&consumergroup=123", wsClient.flow) ~> mainFlow(system) ~> check {
      isWebSocketUpgrade shouldEqual true
      wsClient.expectMessage("")
    }
  }

//  it should "handle server send requests for topics" in {
//    implicit val routeTestTimeout = RouteTestTimeout(10 seconds)
//    Get("/sse/topics?topic=test&consumergroup=1234") ~> mainFlow() ~> check {
//     status.isSuccess() should be (true)
//     val resp = response
//    }
//  }
}
