package com.ovoenergy.umbrella.resources

import com.ovoenergy.commons.lang.ConfigProvider
import spray.http.HttpHeaders._
import spray.http.HttpMethods._
import spray.http.{HttpOrigin, SomeOrigins}
import spray.routing.Directive0
import spray.routing.directives.{BasicDirectives, HeaderDirectives, RespondWithDirectives}

trait AccessHeaders extends BasicDirectives with HeaderDirectives with RespondWithDirectives with ConfigProvider {
  val allowedDomains = config.getStringList("http-service.cors.allowed-domains")

  def allowOrigin(origin: String) = `Access-Control-Allow-Origin`(SomeOrigins(Seq(HttpOrigin(origin))))

  val allowCredentials = `Access-Control-Allow-Credentials`(true)
  val allowMethods = `Access-Control-Allow-Methods`(OPTIONS, GET, POST, PUT, DELETE)

  def cors: Directive0 = optionalHeaderValueByName("Origin") flatMap {
    case Some(origin) if allowedDomains.contains(origin) =>
      respondWithHeaders(allowOrigin(origin), allowCredentials, allowMethods)
    case _ => pass
  }
}