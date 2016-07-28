package com.ovoenergy.umbrella.core

import com.ovoenergy.commons.monitoring.MdcLogging
import com.ovoenergy.commons.service.{DetailedServiceResponseRejectionHandler, HttpServiceActor, PingResource}
import com.ovoenergy.umbrella.dao.ProjectDaoComponent
import com.ovoenergy.umbrella.resources.{AccessHeaders, UmbrellaResource}
import com.ovoenergy.umbrella.services.UmbrellaServiceComponent

class UmbrellaService extends HttpServiceActor with MdcLogging with PingResource
  with UmbrellaResource with UmbrellaServiceComponent with ProjectDaoComponent
  with DetailedServiceResponseRejectionHandler with AccessHeaders {

  def receive = runRoute {
    ping ~
      logRequestResponseContext {
        cors {
          umbrellaResource
        }
      }
  }
}