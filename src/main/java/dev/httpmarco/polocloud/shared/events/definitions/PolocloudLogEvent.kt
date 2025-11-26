package dev.httpmarco.polocloud.shared.events.definitions

import dev.httpmarco.polocloud.shared.events.Event

/**
 * Event is called if a log is created in PoloCloud
 */
class PolocloudLogEvent(val log: String) : Event