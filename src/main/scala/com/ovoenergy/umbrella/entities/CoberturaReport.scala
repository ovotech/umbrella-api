package com.ovoenergy.umbrella.entities

import org.joda.time.DateTime
import spray.http.MediaTypes
import spray.httpx.unmarshalling._

import scala.xml.NodeSeq

import UnmarshallerLifting._

case class CoberturaReport(coverage: Float, date: DateTime, packages: List[(String, Float)])

object CoberturaReport {
  val unmarshaller = fromRequestUnmarshaller {
    fromMessageUnmarshaller {
      Unmarshaller.delegate[NodeSeq, CoberturaReport](MediaTypes.`application/xml`) {
        nodeSeq =>
          val coverage = (nodeSeq \@ "line-rate" toFloat) * 100
          val date = new DateTime(nodeSeq \@ "timestamp" toLong)
          val packages = (nodeSeq \ "packages" \ "package" toList) map {
            node => (node \@ "name", (node \@ "line-rate" toFloat) * 100)
          }
          CoberturaReport(coverage, date, packages)
      }
    }
  }
}