package com.ovoenergy.umbrella.entities

import slick.driver.MySQLDriver.api._

case class Package(packageName: String, coverage: Float)

class PackageT(tag: Tag) extends Table[Package](tag, "PROJECT_PACKAGES") {
  def projectName = column[String]("PROJECT_NAME", O.PrimaryKey)

  def packageName = column[String]("PACKAGE_NAME", O.PrimaryKey)

  def coverage = column[Float]("COVERAGE")

  def * = (packageName, coverage) <>(Package.tupled, Package.unapply)
}