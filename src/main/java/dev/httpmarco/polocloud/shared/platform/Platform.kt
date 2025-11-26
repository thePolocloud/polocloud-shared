package dev.httpmarco.polocloud.shared.platform

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.httpmarco.polocloud.shared.PolocloudSharedKeys
import dev.httpmarco.polocloud.v1.GroupType
import dev.httpmarco.polocloud.v1.platform.PlatformSnapshot

/**
 * Represents a platform with a name, type, and multiple versions.
 *
 * @property name the name of the platform
 * @property type the group type of the platform
 * @property versions the list of platform versions
 */
open class Platform(
    val name: String,
    val type: GroupType,
    val versions: List<PlatformVersion>
) {

    companion object {

        /**
         * Creates a Platform instance from a PlatformSnapshot.
         *
         * @param snapshot the snapshot to bind
         * @return a Platform instance
         */
        fun fromSnapshot(snapshot: PlatformSnapshot): Platform {
            return Platform(
                name = snapshot.name,
                type = snapshot.type,
                versions = snapshot.versionsList.map { PlatformVersion.Companion.from(it) }
            )
        }
    }

    /**
     * Converts this Platform into a PlatformSnapshot.
     *
     * @return PlatformSnapshot representing this platform
     */
    fun toSnapshot(): PlatformSnapshot = PlatformSnapshot.newBuilder()
        .setName(name)
        .setType(type)
        .addAllVersions(versions.map { it.to() })
        .build()
}

/**
 * Converts the Platform to a JSON representation.
 *
 * @return JsonObject representing this platform
 */
fun Platform.toJson(): JsonObject {
    val platformVersions = JsonArray().apply {
        versions.forEach { version ->
            add(JsonObject().apply { addProperty(PolocloudSharedKeys.VERSION, version.version) })
        }
    }

    return JsonObject().apply {
        addProperty(PolocloudSharedKeys.NAME, name)
        addProperty(PolocloudSharedKeys.TYPE, type.name)
        add(PolocloudSharedKeys.VERSIONS, platformVersions)
    }
}