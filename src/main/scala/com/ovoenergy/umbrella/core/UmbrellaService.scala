package com.ovoenergy.umbrella.core

import com.ovoenergy.commons.monitoring.MdcLogging
import com.ovoenergy.commons.service.{DetailedServiceResponseRejectionHandler, HttpServiceActor, PingResource}
import com.ovoenergy.umbrella.resources.{AccessHeaders, UmbrellaResource}

class UmbrellaService extends HttpServiceActor with MdcLogging with PingResource
  with UmbrellaResource with DetailedServiceResponseRejectionHandler with AccessHeaders {

  def receive = runRoute {
    ping ~
      logRequestResponseContext {
        cors {
          umbrellaResource
        }
      }
  }
}