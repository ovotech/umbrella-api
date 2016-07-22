package com.ovoenergy.umbrella.dao

import slick.backend.DatabaseConfig
import slick.driver.MySQLDriver

trait UmbrellaDatabaseComponent {
  val database = DatabaseConfig.forConfig[MySQLDriver]("database").db
}