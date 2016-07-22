package com.ovoenergy.umbrella.entities

import slick.driver.MySQLDriver.api._

case class Project(name: String, version: String, coverage: Float)

class ProjectT(tag: Tag) extends Table[Project](tag, "PROJECTS") {
  def name = column[String]("PROJECT_NAME", O.PrimaryKey)

  def version = column[String]("VERSION", O.PrimaryKey)

  def coverage = column[Float]("COVERAGE")

  def * = (name, version, coverage) <>(Project.tupled, Project.unapply)
}