package com.ovoenergy.umbrella.resources

import com.ovoenergy.commons.service.HttpService
import com.ovoenergy.umbrella.entities._
import com.ovoenergy.umbrella.services.UmbrellaServiceComponent
import spray.httpx.unmarshalling.UnmarshallerLifting

trait UmbrellaResource extends HttpService with UnmarshallerLifting {
  self: UmbrellaServiceComponent =>

  val umbrellaResource =
    path("projects") {
      get {
        complete {
          umbrellaService.listProjects
        }
      }
    } ~ path("project" / """[\w-]+""".r) { project =>
      get {
        complete {
          umbrellaService.projectDetails(project)
        }
      }
    } ~ path("cobertura") {
      post {
        headerValueByName("Project-Name") { project =>
          headerValueByName("Project-Version") { version =>
            entity(as[CoberturaReport](CoberturaReport.unmarshaller)) { report =>
              complete {
                umbrellaService.loadReport(project, version, report)
              }
            }
          }
        }
      }
    }
}