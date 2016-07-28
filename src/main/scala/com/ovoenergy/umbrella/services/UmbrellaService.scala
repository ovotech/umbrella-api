package com.ovoenergy.umbrella.services

import com.ovoenergy.commons.lang.ExecutionContextProvider
import com.ovoenergy.umbrella.dao.{ProjectDao, ProjectDaoComponent}
import com.ovoenergy.umbrella.entities.{ProjectDetailsResponse, ProjectVersion, ProjectPackage, CoberturaReport}

import scala.concurrent.{ExecutionContext, Future}

class UmbrellaService(projectDao: ProjectDao)(implicit executionContext: ExecutionContext) {
  def listProjects = projectDao.listProjects

  def projectDetails(project: String) = {
    val packages: Future[Seq[ProjectPackage]] = projectDao.listPackagesIn(project)
    val versions: Future[Seq[ProjectVersion]] = projectDao.listVersionsByDateFor(project)
    (packages zip versions) map ProjectDetailsResponse.tupled
  }

  def loadReport(project: String, version: String, report: CoberturaReport) = for {
    result <- projectDao.setProject(project, version, report.coverage)
    _ <- projectDao.addVersion(project, version, report.date, report.coverage)
    _ <- projectDao.setPackages(project, report.packages)
  } yield result
}

trait UmbrellaServiceComponent extends ExecutionContextProvider{
  self: ProjectDaoComponent =>
  lazy val umbrellaService = new UmbrellaService(projectDao)
}