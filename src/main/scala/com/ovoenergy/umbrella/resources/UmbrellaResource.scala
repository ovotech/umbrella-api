package com.ovoenergy.umbrella.resources

import com.ovoenergy.commons.service.HttpService
import com.ovoenergy.umbrella.dao.ProjectDaoComponent
import com.ovoenergy.umbrella.entities._
import spray.httpx.unmarshalling.UnmarshallerLifting

import scala.concurrent.Future

trait UmbrellaResource extends HttpService with UnmarshallerLifting with ProjectDaoComponent {
  val umbrellaResource =
    path("projects") {
      get {
        complete {
          projectDao.listProjects
        }
      }
    } ~ path("project" / """[\w-]+""".r) { project =>
      get {
        complete {
          val packages: Future[Seq[ProjectPackage]] = projectDao.listPackagesIn(project)
          val versions: Future[Seq[ProjectVersion]] = projectDao.listVersionsByDateFor(project)
          (packages zip versions) map ProjectDetailsResponse.tupled
        }
      }
    } ~ path("cobertura") {
      post {
        headerValueByName("Project-Name") { projectName =>
          headerValueByName("Project-Version") { version =>
            entity(as[CoberturaReport](CoberturaReport.unmarshaller)) { e =>
              complete {
                for {
                  result <- projectDao.setProject(projectName, version, e.coverage)
                  _ <- projectDao.addVersion(projectName, version, e.date, e.coverage)
                  _ <- projectDao.setPackages(projectName, e.packages)
                } yield result
              }
            }
          }
        }
      }
    }
}