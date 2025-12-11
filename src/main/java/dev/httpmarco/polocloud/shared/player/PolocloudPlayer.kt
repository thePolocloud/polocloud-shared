package dev.httpmarco.polocloud.shared.player

import com.google.gson.JsonObject
import dev.httpmarco.polocloud.shared.PolocloudSharedKeys
import dev.httpmarco.polocloud.v1.player.PlayerSnapshot
import java.util.UUID

/**
 * Represents a player in the PoloCloud system.
 *
 * @property name the name of the player
 * @property uniqueId the unique identifier (UUID) of the player
 * @property currentServerName the name of the service the player is currently connected to
 * @property currentProxyName the name of the proxy the player is currently connected to
 */
open class PolocloudPlayer(
    val name: String,
    val uniqueId: UUID,
    val currentServerName: String,
    val currentProxyName: String
) {

    /**
     * Returns the string representation of the player's UUID.
     */
    fun uniqueId(): String = uniqueId.toString()

    companion object {

        /**
         * Creates a PolocloudPlayer from a PlayerSnapshot.
         *
         * @param snapshot the snapshot to bind
         * @return a PolocloudPlayer instance
         */
        fun from(snapshot: PlayerSnapshot): PolocloudPlayer {
            return PolocloudPlayer(
                name = snapshot.name,
                uniqueId = UUID.fromString(snapshot.uniqueId),
                currentServerName = snapshot.currentServerName,
                currentProxyName = snapshot.currentProxyName
            )
        }
    }

    /**
     * Converts this PolocloudPlayer into a PlayerSnapshot.
     *
     * @return PlayerSnapshot representing this player
     */
    fun to(): PlayerSnapshot {
        return PlayerSnapshot.newBuilder()
            .setName(name)
            .setUniqueId(uniqueId.toString())
            .setCurrentServerName(currentServerName)
            .setCurrentProxyName(currentProxyName)
            .build()
    }
}

/**
 * Converts the PolocloudPlayer to a JSON object using predefined keys.
 *
 * @return JsonObject representing this player
 */
fun PolocloudPlayer.toJson(): JsonObject {
    return JsonObject().apply {
        addProperty(PolocloudSharedKeys.NAME, name)
        addProperty(PolocloudSharedKeys.UNIQUE_ID, uniqueId.toString())
        addProperty(PolocloudSharedKeys.CURRENT_SERVER_NAME, currentServerName)
        addProperty(PolocloudSharedKeys.CURRENT_PROXY_NAME, currentProxyName)
    }
}
