package com.ovoenergy.umbrella.resources

import com.ovoenergy.commons.service.HttpService
import com.ovoenergy.umbrella.dao.ProjectDaoComponent
import com.ovoenergy.umbrella.entities.{Package, ProjectDetailsResponse, ProjectVersion}

import scala.concurrent.Future

trait UmbrellaResource extends HttpService with ProjectDaoComponent with AccessHeaders {
  val umbrellaResource =
    path("projects") {
      get {
        complete {
          projectDao.projects
        }
      }
    } ~ path("project" / """[\w-]+""".r) { project =>
      get {
        complete {
          val packages: Future[Seq[Package]] = projectDao.packages(project)
          val versions: Future[Seq[ProjectVersion]] = projectDao.versions(project)
          (packages zip versions) map ProjectDetailsResponse.tupled
        }
      }
    }

}