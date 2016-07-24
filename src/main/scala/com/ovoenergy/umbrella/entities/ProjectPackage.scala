package com.ovoenergy.umbrella.entities

import slick.driver.MySQLDriver.api._

case class ProjectPackage(projectName: String, packageName: String, coverage: Float)

class ProjectPackageT(tag: Tag) extends Table[ProjectPackage](tag, "PROJECT_PACKAGES") {
  def projectName = column[String]("PROJECT_NAME", O.PrimaryKey)

  def packageName = column[String]("PACKAGE_NAME", O.PrimaryKey)

  def coverage = column[Float]("COVERAGE")

  def * = (projectName, packageName, coverage) <>(ProjectPackage.tupled, ProjectPackage.unapply)
}