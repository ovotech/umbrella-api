package com.ovoenergy.umbrella.dao

import com.ovoenergy.umbrella.entities.{PackageT, ProjectT, ProjectVersionT}
import slick.driver.MySQLDriver.api._
import slick.lifted.TableQuery

class ProjectDao(database: Database) {
  def projects = database.run {
    TableQuery[ProjectT].result
  }

  def packages(project: String) = database.run {
    TableQuery[PackageT].filter(_.projectName === project).result
  }

  def versions(project: String) = database.run {
    TableQuery[ProjectVersionT].filter(_.projectName === project).result
  }
}

trait ProjectDaoComponent extends UmbrellaDatabaseComponent {
  val projectDao = new ProjectDao(database)
}