package com.ovoenergy.umbrella.dao

import com.github.tototoshi.slick.MySQLJodaSupport._
import com.ovoenergy.commons.lang.ExecutionContextProvider
import com.ovoenergy.umbrella.entities._
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext

class ProjectDao(database: Database)(implicit executionContext: ExecutionContext) {
  val projects = TableQuery[ProjectT]
  val packages = TableQuery[ProjectPackageT]
  val versions = TableQuery[ProjectVersionT]

  def listProjects = database.run {
    projects.sortBy(_.name).result
  }

  def listPackagesIn(project: String) = database.run {
    packages.filter(_.projectName === project).sortBy(_.packageName).result
  }

  def listVersionsByDateFor(project: String) = database.run {
    versions.filter(_.projectName === project).sortBy(_.date.desc).result
  }

  def setProject(name: String, version: String, coverage: Float) = database.run {
    DBIO.seq(
      projects.filter(_.name === name).delete,
      projects += Project(name, version, coverage)
    )
  }

  def addVersion(projectName: String, version: String, date: DateTime, coverage: Float) = database.run {
    DBIO.seq(
      versions.filter(v => v.projectName === projectName && v.version === version).delete,
      versions += ProjectVersion(projectName, version, date, coverage)
    )
  }

  def setPackages(projectName: String, packageCoverages: List[(String, Float)]) = database.run {
    DBIO.seq(
      packages.filter(_.projectName === projectName).delete,
      packages ++= packageCoverages map {
        case (name, coverage) =>
          ProjectPackage(projectName, name, coverage)
      }
    )
  }
}

trait ProjectDaoComponent extends UmbrellaDatabaseComponent with ExecutionContextProvider {
  val projectDao = new ProjectDao(database)
}