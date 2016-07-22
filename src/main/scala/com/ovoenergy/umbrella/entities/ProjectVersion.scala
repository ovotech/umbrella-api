package com.ovoenergy.umbrella.entities

import com.github.tototoshi.slick.MySQLJodaSupport._
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._

case class ProjectVersion(projectName: String, version: String, date: DateTime, coverage: Double)

class ProjectVersionT(tag: Tag) extends Table[ProjectVersion](tag, "PROJECT_VERSIONS") {
  def projectName = column[String]("PROJECT_NAME", O.PrimaryKey)

  def version = column[String]("VERSION", O.PrimaryKey)

  def date = column[DateTime]("DATE")

  def coverage = column[Double]("COVERAGE")

  def * = (projectName, version, date, coverage) <>(ProjectVersion.tupled, ProjectVersion.unapply)
}