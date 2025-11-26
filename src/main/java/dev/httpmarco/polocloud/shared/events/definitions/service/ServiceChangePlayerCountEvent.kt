package dev.httpmarco.polocloud.shared.events.definitions.service

import dev.httpmarco.polocloud.shared.events.Event
import dev.httpmarco.polocloud.shared.service.Service

/**
 * This event is called when the player count of a service changes.
 *
 * @param service The service which changed its player count.
 */
class ServiceChangePlayerCountEvent(val service: Service): Event