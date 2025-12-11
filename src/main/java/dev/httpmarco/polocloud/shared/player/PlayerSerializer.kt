package dev.httpmarco.polocloud.shared.player

import com.google.gson.*
import dev.httpmarco.polocloud.shared.PolocloudSharedKeys
import java.lang.reflect.Type
import java.util.UUID

/**
 * Gson serializer and deserializer for [PolocloudPlayer].
 *
 * Serializes a PolocloudPlayer to JSON and deserializes JSON into a PolocloudPlayer
 * using predefined keys from [PolocloudSharedKeys].
 */
class PlayerSerializer : JsonSerializer<PolocloudPlayer>, JsonDeserializer<PolocloudPlayer> {

    /**
     * Serializes a [PolocloudPlayer] into a [JsonElement].
     *
     * @param src the PolocloudPlayer to serialize
     * @param typeOfSrc the type of the source object
     * @param context the JSON serialization context
     * @return a JsonElement representing the player
     */
    override fun serialize(
        src: PolocloudPlayer,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonObject().apply {
            addProperty(PolocloudSharedKeys.UNIQUE_ID, src.uniqueId())
            addProperty(PolocloudSharedKeys.NAME, src.name)
            addProperty(PolocloudSharedKeys.CURRENT_SERVER_NAME, src.currentServerName)
            addProperty(PolocloudSharedKeys.CURRENT_PROXY_NAME, src.currentProxyName)
        }
    }

    /**
     * Deserializes a [JsonElement] into a [PolocloudPlayer].
     *
     * @param json the JSON element to deserialize
     * @param typeOfT the target type
     * @param context the JSON deserialization context
     * @return a PolocloudPlayer instance represented by the JSON
     */
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): PolocloudPlayer {
        val obj = json.asJsonObject

        val uniqueId = UUID.fromString(obj.get(PolocloudSharedKeys.UNIQUE_ID).asString)
        val name = obj.get(PolocloudSharedKeys.NAME).asString
        val currentServer = obj.get(PolocloudSharedKeys.CURRENT_SERVER_NAME).asString
        val currentProxy = obj.get(PolocloudSharedKeys.CURRENT_PROXY_NAME).asString
        return PolocloudPlayer(
            name = name,
            uniqueId = uniqueId,
            currentServerName = currentServer,
            currentProxyName = currentProxy
        )
    }
}
