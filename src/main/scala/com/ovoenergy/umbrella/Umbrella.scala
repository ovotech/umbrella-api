package com.ovoenergy.umbrella

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import com.ovoenergy.commons.lang.ConfigProvider
import com.ovoenergy.commons.service.TcpListener
import com.ovoenergy.umbrella.core.UmbrellaService
import spray.can.Http

object Umbrella extends App with ConfigProvider {
  implicit val system = ActorSystem("umbrella-service")
  val port = config.getInt("http-service.port")
  val tcpListener = system.actorOf(Props[TcpListener], "tcp-listener")

  val httpService = system.actorOf(Props[UmbrellaService])
  IO(Http).tell(Http.Bind(httpService, interface = "0.0.0.0", port = port), tcpListener)
}