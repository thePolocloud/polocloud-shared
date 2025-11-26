package dev.httpmarco.polocloud.dev.httpmarco.polocloud.shared.groups

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import dev.httpmarco.polocloud.shared.PolocloudSharedKeys
import dev.httpmarco.polocloud.dev.httpmarco.polocloud.shared.platform.PlatformIndex
import dev.httpmarco.polocloud.dev.httpmarco.polocloud.shared.properties.PropertyHolder
import dev.httpmarco.polocloud.shared.template.Template
import dev.httpmarco.polocloud.v1.groups.GroupSnapshot

/**
 * Represents a cloud group with memory, service, platform, templates and custom properties.
 *
 * @property name the name of the group
 * @property minMemory the minimum memory allocated per service
 * @property maxMemory the maximum memory allocated per service
 * @property minOnlineService the minimum number of online services
 * @property maxOnlineService the maximum number of online services
 * @property platform the platform this group belongs to
 * @property percentageToStartNewService the threshold percentage to start a new service
 * @property createdAt the creation timestamp
 * @property templates the templates associated with this group
 * @property properties additional custom properties of this group
 */
open class Group(
    val name: String,
    minMemory: Int,
    maxMemory: Int,
    minOnlineService: Int,
    maxOnlineService: Int,
    val platform: PlatformIndex,
    percentageToStartNewService: Double,
    val createdAt: Long,
    val templates: List<Template>,
    val properties: PropertyHolder
) {

    var minMemory: Int = minMemory
        protected set

    var maxMemory: Int = maxMemory
        protected set

    var minOnlineService: Int = minOnlineService
        protected set

    var maxOnlineService: Int = maxOnlineService
        protected set

    var percentageToStartNewService: Double = percentageToStartNewService
        protected set

    companion object {

        /**
         * Creates a Group instance from a GroupSnapshot.
         *
         * @param snapshot the snapshot to bind
         * @return a Group instance
         */
        fun from(snapshot: GroupSnapshot): Group {
            val propertyHolder = PropertyHolder.empty()

            snapshot.propertiesMap.forEach { (key, value) ->
                val primitive = when {
                    value.lowercase().toBooleanStrictOrNull() != null -> JsonPrimitive(value.toBoolean())
                    value.toIntOrNull() != null -> JsonPrimitive(value.toInt())
                    value.toDoubleOrNull() != null -> JsonPrimitive(value.toDouble())
                    value.toFloatOrNull() != null -> JsonPrimitive(value.toFloat())
                    else -> JsonPrimitive(value)
                }
                propertyHolder.raw(key, primitive)
            }

            return Group(
                name = snapshot.name,
                minMemory = snapshot.minimumMemory,
                maxMemory = snapshot.maximumMemory,
                minOnlineService = snapshot.minimumOnline,
                maxOnlineService = snapshot.maximumOnline,
                platform = PlatformIndex(snapshot.platform.name, snapshot.platform.version),
                percentageToStartNewService = snapshot.percentageToStartNewService,
                createdAt = snapshot.createdAt,
                templates = Template.fromSnapshotList(snapshot.templatesList),
                properties = propertyHolder
            )
        }
    }

    /**
     * Converts this Group into a GroupSnapshot.
     *
     * @return GroupSnapshot representing this group
     */
    fun to(): GroupSnapshot = GroupSnapshot.newBuilder()
        .setName(name)
        .setMinimumMemory(minMemory)
        .setMaximumMemory(maxMemory)
        .setMinimumOnline(minOnlineService)
        .setMaximumOnline(maxOnlineService)
        .setPlatform(platform.to())
        .setPercentageToStartNewService(percentageToStartNewService)
        .setCreatedAt(createdAt)
        .addAllTemplates(templates.map { it.to() })
        .putAllProperties(properties.all().mapValues { it.value.toString() })
        .build()
}

/**
 * Converts the Group to a JSON representation.
 *
 * @return JsonObject representing this group
 */
fun Group.toJson(): JsonObject {
    val groupPlatform = JsonObject().apply {
        addProperty(PolocloudSharedKeys.NAME, platform.name)
        addProperty(PolocloudSharedKeys.VERSION, platform.version)
    }

    val groupTemplates = JsonArray().apply {
        templates.forEach { add(it.name) }
    }

    val groupProperties = JsonObject().apply {
        properties.all().forEach { (key, value) -> add(key, value) }
    }

    return JsonObject().apply {
        addProperty(PolocloudSharedKeys.NAME, name)
        addProperty(PolocloudSharedKeys.MIN_MEMORY, minMemory)
        addProperty(PolocloudSharedKeys.MAX_MEMORY, maxMemory)
        addProperty(PolocloudSharedKeys.MIN_ONLINE, minOnlineService)
        addProperty(PolocloudSharedKeys.MAX_ONLINE, maxOnlineService)
        addProperty(PolocloudSharedKeys.PERCENTAGE_TO_START, percentageToStartNewService)
        addProperty(PolocloudSharedKeys.CREATED_AT, createdAt)
        add(PolocloudSharedKeys.PLATFORM, groupPlatform)
        add(PolocloudSharedKeys.TEMPLATES, groupTemplates)
        add(PolocloudSharedKeys.PROPERTIES, groupProperties)
    }
}
