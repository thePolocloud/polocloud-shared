package dev.httpmarco.polocloud.shared.events.definitions

import dev.httpmarco.polocloud.dev.httpmarco.polocloud.shared.events.Event
import dev.httpmarco.polocloud.shared.player.PolocloudPlayer

/**
 * Event is called if a player joins the network
 */
class PlayerLeaveEvent(val player: PolocloudPlayer) : Event