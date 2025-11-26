package dev.httpmarco.polocloud.shared.service

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.httpmarco.polocloud.shared.PolocloudSharedKeys
import dev.httpmarco.polocloud.shared.template.Template
import dev.httpmarco.polocloud.shared.polocloudShared
import dev.httpmarco.polocloud.v1.groups.GroupType
import dev.httpmarco.polocloud.v1.services.ServiceSnapshot
import dev.httpmarco.polocloud.v1.services.ServiceState

/**
 * Represents a cloud service instance with state, properties, templates, and system metrics.
 *
 * @property groupName the group this service belongs to
 * @property id the unique ID of the service within the group
 * @property state the current state of the service
 * @property type the type of the group
 * @property properties key-value pairs representing custom service properties
 * @property hostname the host where the service is running
 * @property port the port the service is listening on
 * @property templates list of templates associated with the service
 * @property information runtime information about the service
 */
open class Service(
    val groupName: String,
    val id: Int,
    var state: ServiceState,
    val type: GroupType,
    var properties: Map<String, String>,
    hostname: String,
    val port: Int,
    var templates: List<Template>,
    val information: ServiceInformation,
    minMemory: Int,
    maxMemory: Int,
    playerCount: Int = -1,
    maxPlayerCount: Int = -1,
    memoryUsage: Double = -1.0,
    cpuUsage: Double = -1.0,
    motd: String = ""
) {

    var minMemory: Int = minMemory
        protected set

    var maxMemory: Int = maxMemory
        protected set

    var maxPlayerCount: Int = maxPlayerCount
        protected set

    var playerCount: Int = playerCount
        protected set

    var memoryUsage: Double = memoryUsage
        protected set

    var cpuUsage: Double = cpuUsage
        protected set

    var motd: String = motd
        protected set

    var hostname: String = hostname
        protected set

    /**
     * Returns the full name of the service in the format "{groupName}-{id}".
     */
    fun name(): String = "$groupName-$id"

    /**
     * Changes the state of this service.
     *
     * @param state the new state
     */
    open fun changeState(state: ServiceState) {
        this.state = state
    }

    companion object {

        /**
         * Creates a Service instance from a ServiceSnapshot.
         *
         * @param snapshot the snapshot to bind
         * @return a Service instance
         */
        fun fromSnapshot(snapshot: ServiceSnapshot): Service {
            return Service(
                groupName = snapshot.groupName,
                id = snapshot.id,
                state = snapshot.state,
                type = snapshot.serverType,
                properties = snapshot.propertiesMap,
                hostname = snapshot.hostname,
                port = snapshot.port,
                templates = Template.fromSnapshotList(snapshot.templatesList),
                information = ServiceInformation.Companion.from(snapshot.information),
                minMemory = snapshot.minimumMemory,
                maxMemory = snapshot.maximumMemory,
                maxPlayerCount = snapshot.maxPlayerCount,
                playerCount = snapshot.playerCount,
                memoryUsage = snapshot.memoryUsage,
                cpuUsage = snapshot.cpuUsage,
                motd = snapshot.motd
            )
        }
    }

    /**
     * Converts this Service into a ServiceSnapshot.
     *
     * @return ServiceSnapshot representing this service
     */
    fun toSnapshot(): ServiceSnapshot = ServiceSnapshot.newBuilder()
        .setGroupName(groupName)
        .setId(id)
        .setState(state)
        .setServerType(type)
        .putAllProperties(properties)
        .setHostname(hostname)
        .addAllTemplates(templates.map { it.to() })
        .setInformation(information.to())
        .setMinimumMemory(minMemory)
        .setMaximumMemory(maxMemory)
        .setMaxPlayerCount(maxPlayerCount)
        .setPlayerCount(playerCount)
        .setPort(port)
        .setMotd(motd)
        .build()

    /**
     * Shuts down the service via the global shared service provider.
     */
    fun shutdown() {
        polocloudShared.serviceProvider().shutdownService(name())
    }

    override fun equals(other: Any?): Boolean =
        other is Service &&
                groupName == other.groupName &&
                id == other.id &&
                state == other.state &&
                type == other.type &&
                properties == other.properties &&
                hostname == other.hostname &&
                port == other.port

    override fun hashCode(): Int =
        listOf(groupName, id, state, type, properties, hostname, port).hashCode()
}

/**
 * Converts the Service to a JSON representation.
 *
 * @return JsonObject representing this service
 */
fun Service.toJson(): JsonObject {
    val serviceTemplates = JsonArray().apply {
        templates.forEach { template ->
            add(JsonObject().apply {
                addProperty(PolocloudSharedKeys.NAME, template.name)
                addProperty(PolocloudSharedKeys.SIZE, template.size())
            })
        }
    }

    val serviceProperties = JsonObject().apply {
        properties.forEach { (key, value) -> addProperty(key, value) }
    }

    return JsonObject().apply {
        addProperty(PolocloudSharedKeys.NAME, name())
        addProperty(PolocloudSharedKeys.STATE, state.name)
        addProperty(PolocloudSharedKeys.TYPE, type.name)
        addProperty(PolocloudSharedKeys.GROUP_NAME, groupName)
        addProperty(PolocloudSharedKeys.HOSTNAME, hostname)
        addProperty(PolocloudSharedKeys.PORT, port)
        add(PolocloudSharedKeys.TEMPLATES, serviceTemplates)
        add(PolocloudSharedKeys.PROPERTIES, serviceProperties)
        addProperty(PolocloudSharedKeys.MIN_MEMORY, minMemory)
        addProperty(PolocloudSharedKeys.MAX_MEMORY, maxMemory)
        addProperty(PolocloudSharedKeys.PLAYER_COUNT, playerCount)
        addProperty(PolocloudSharedKeys.MAX_PLAYER_COUNT, maxPlayerCount)
        addProperty(PolocloudSharedKeys.MEMORY_USAGE, memoryUsage)
        addProperty(PolocloudSharedKeys.CPU_USAGE, cpuUsage)
        addProperty(PolocloudSharedKeys.MOTD, motd)
    }
}
