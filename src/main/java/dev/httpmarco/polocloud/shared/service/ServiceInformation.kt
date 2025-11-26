package dev.httpmarco.polocloud.dev.httpmarco.polocloud.shared.service

import com.google.gson.Gson
import dev.httpmarco.polocloud.v1.services.ServiceInformationSnapshot

/**
 * Represents runtime information for a service.
 *
 * @property createdAt the timestamp when the service was created
 */
open class ServiceInformation(val createdAt: Long) {

    companion object {
        private val gson = Gson()

        /**
         * Creates a ServiceInformation instance from a ServiceInformationSnapshot.
         *
         * @param snapshot the snapshot to bind
         * @return a ServiceInformation instance
         */
        fun from(snapshot: ServiceInformationSnapshot): ServiceInformation {
            return ServiceInformation(snapshot.createdAt)
        }

        /**
         * Creates a ServiceInformation instance from a JSON string.
         *
         * @param string JSON string representing the ServiceInformation
         * @return a ServiceInformation instance
         */
        fun fromJson(string: String): ServiceInformation {
            return gson.fromJson(string, ServiceInformation::class.java)
        }
    }

    /**
     * Converts this ServiceInformation into a ServiceInformationSnapshot.
     *
     * @return ServiceInformationSnapshot representing this instance
     */
    fun to(): ServiceInformationSnapshot {
        return ServiceInformationSnapshot.newBuilder()
            .setCreatedAt(createdAt)
            .build()
    }

    /**
     * Converts this ServiceInformation into a JSON string.
     *
     * @return JSON string representing this instance
     */
    override fun toString(): String = gson.toJson(this)
}
