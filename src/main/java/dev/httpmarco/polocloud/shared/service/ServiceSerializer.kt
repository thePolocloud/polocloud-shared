package dev.httpmarco.polocloud.shared.service

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import dev.httpmarco.polocloud.shared.PolocloudSharedKeys
import dev.httpmarco.polocloud.shared.template.Template
import dev.httpmarco.polocloud.v1.GroupType
import dev.httpmarco.polocloud.v1.services.ServiceState
import java.lang.reflect.Type

class ServiceSerializer : JsonSerializer<Service>, JsonDeserializer<Service> {

    override fun serialize(
        src: Service,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val data = JsonObject()

        data.addProperty(PolocloudSharedKeys.NAME, src.groupName)
        data.addProperty(PolocloudSharedKeys.ID, src.id)
        data.addProperty(PolocloudSharedKeys.HOSTNAME, src.hostname)
        data.addProperty(PolocloudSharedKeys.PORT, src.port)
        data.addProperty(PolocloudSharedKeys.STATE, src.state.name)
        data.addProperty(PolocloudSharedKeys.TYPE, src.type.name)
        data.addProperty(PolocloudSharedKeys.INFORMATION, src.information.toString())
        data.addProperty(PolocloudSharedKeys.MIN_MEMORY, src.minMemory)
        data.addProperty(PolocloudSharedKeys.MAX_MEMORY, src.maxMemory)
        data.addProperty(PolocloudSharedKeys.MAX_PLAYER_COUNT, src.maxPlayerCount)
        data.addProperty(PolocloudSharedKeys.PLAYER_COUNT, src.playerCount)
        data.addProperty(PolocloudSharedKeys.MEMORY_USAGE, src.memoryUsage)
        data.addProperty(PolocloudSharedKeys.CPU_USAGE, src.cpuUsage)
        data.addProperty(PolocloudSharedKeys.MOTD, src.motd)

        data.add(PolocloudSharedKeys.TEMPLATES, context.serialize(src.templates))
        data.add(PolocloudSharedKeys.PROPERTIES, context.serialize(src.properties.map { it.key to it.value }.toMap()))

        return data
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Service {
        val data = json.asJsonObject

        val name = data.get(PolocloudSharedKeys.NAME).asString
        val id = data.get(PolocloudSharedKeys.ID).asInt
        val hostname = data.get(PolocloudSharedKeys.HOSTNAME).asString
        val port = data.get(PolocloudSharedKeys.PORT).asInt
        val type = GroupType.valueOf(data.get(PolocloudSharedKeys.TYPE).asString)
        val state = ServiceState.valueOf(data.get(PolocloudSharedKeys.STATE).asString)
        val templatesType = object : TypeToken<List<Template>>() {}.type
        val templates = context.deserialize<List<Template>>(data.get(PolocloudSharedKeys.TEMPLATES), templatesType)
        val information = ServiceInformation.fromJson(data.get(PolocloudSharedKeys.INFORMATION).asString)
        val minMemory = data.get(PolocloudSharedKeys.MIN_MEMORY).asInt
        val maxMemory = data.get(PolocloudSharedKeys.MAX_MEMORY).asInt
        val maxPlayerCount = data.get(PolocloudSharedKeys.MAX_PLAYER_COUNT).asInt
        val playerCount = data.get(PolocloudSharedKeys.PLAYER_COUNT).asInt
        val memoryUsage = data.get(PolocloudSharedKeys.MEMORY_USAGE).asDouble
        val cpuUsage = data.get(PolocloudSharedKeys.CPU_USAGE).asDouble
        val motd = data.get(PolocloudSharedKeys.MOTD).asString

        val propertiesType = object : TypeToken<Map<String, String>>() {}.type
        val properties = context.deserialize<Map<String, String>>(data.get(PolocloudSharedKeys.PROPERTIES), propertiesType)

        return Service(
            name,
            id,
            state,
            type,
            properties,
            hostname,
            port,
            templates,
            information,
            minMemory,
            maxMemory,
            playerCount,
            maxPlayerCount,
            memoryUsage,
            cpuUsage,
            motd
        )
    }
}