package com.ovoenergy.umbrella.entities

import org.joda.time.DateTime
import spray.http.{HttpEntity, MediaTypes}
import spray.httpx.unmarshalling.UnmarshallerLifting._
import spray.httpx.unmarshalling._

import scala.xml.XML

case class CoberturaReport(coverage: Float, date: DateTime, packages: List[(String, Float)])

object CoberturaReport {
  val unmarshaller = fromRequestUnmarshaller {
    fromMessageUnmarshaller {
      Unmarshaller[CoberturaReport](MediaTypes.`application/xml`) {
        case HttpEntity.NonEmpty(contentType, data) =>
          val report = XML.loadString(data.asString(contentType.charset))
          val coverage = (report \@ "line-rate" toFloat) * 100
          val date = new DateTime(report \@ "timestamp" toLong)
          val packages = (report \ "packages" \ "package" toList) map {
            node => (node \@ "name", (node \@ "line-rate" toFloat) * 100)
          }
          CoberturaReport(coverage, date, packages)
      }
    }
  }
}