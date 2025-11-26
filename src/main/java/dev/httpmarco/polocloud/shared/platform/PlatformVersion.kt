package dev.httpmarco.polocloud.dev.httpmarco.polocloud.shared.platform

import dev.httpmarco.polocloud.v1.platform.PlatformVersionSnapshot

/**
 * Represents a specific version of a platform.
 *
 * @property version the version string of the platform
 */
data class PlatformVersion(
    val version: String
) {

    companion object {

        /**
         * Creates a PlatformVersion instance from a PlatformVersionSnapshot.
         *
         * @param snapshot the snapshot to bind
         * @return a PlatformVersion instance
         */
        fun from(snapshot: PlatformVersionSnapshot): PlatformVersion {
            return PlatformVersion(snapshot.version)
        }
    }

    /**
     * Converts this PlatformVersion into a PlatformVersionSnapshot.
     *
     * @return PlatformVersionSnapshot representing this version
     */
    fun to(): PlatformVersionSnapshot = PlatformVersionSnapshot.newBuilder()
        .setVersion(version)
        .build()
}
