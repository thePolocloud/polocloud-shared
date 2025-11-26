package dev.httpmarco.polocloud.shared.information

import dev.httpmarco.polocloud.v1.information.CloudInformationSnapshot

/**
 * Represents immutable system information about a cloud instance at a specific point in time.
 *
 * This data class contains runtime metrics such as CPU usage, memory usage, Java version,
 * subscribed event count, and timestamps. It acts as a bridge between the internal model and
 * the protocol buffer {@link CloudInformationSnapshot}.
 *
 * @property started The timestamp (epoch millis) when the cloud instance was started.
 * @property runtime The human-readable runtime version or identifier of the cloud instance.
 * @property javaVersion The Java version used by the cloud runtime.
 * @property cpuUsage The current CPU usage percentage.
 * @property usedMemory The currently used heap memory in megabytes.
 * @property maxMemory The maximum available heap memory in megabytes.
 * @property subscribedEvents The number of events currently subscribed by the cloud instance.
 * @property timestamp The timestamp (epoch millis) when this snapshot was captured.
 */
data class CloudInformation(
    val started: Long,
    val runtime: String,
    val javaVersion: String,
    val cpuUsage: Double,
    val usedMemory: Double,
    val maxMemory: Double,
    val subscribedEvents: Int,
    val timestamp: Long
) {

    companion object {

        /**
         * Creates a {@link CloudInformation} instance from a protobuf
         * {@link CloudInformationSnapshot}.
         *
         * @param snapshot The snapshot to convert.
         * @return A new {@link CloudInformation} containing the data from the snapshot.
         */
        fun from(snapshot: CloudInformationSnapshot) = CloudInformation(
            started = snapshot.started,
            runtime = snapshot.runtime,
            javaVersion = snapshot.javaVersion,
            cpuUsage = snapshot.cpuUsage,
            usedMemory = snapshot.usedMemory,
            maxMemory = snapshot.maxMemory,
            subscribedEvents = snapshot.subscribedEvents,
            timestamp = snapshot.timestamp
        )
    }

    /**
     * Converts this information model into a protocol buffer snapshot
     * ({@link CloudInformationSnapshot}).
     *
     * @return A new protobuf snapshot containing this instance's data.
     */
    fun toSnapshot(): CloudInformationSnapshot =
        CloudInformationSnapshot.newBuilder()
            .setStarted(started)
            .setRuntime(runtime)
            .setJavaVersion(javaVersion)
            .setCpuUsage(cpuUsage)
            .setUsedMemory(usedMemory)
            .setMaxMemory(maxMemory)
            .setSubscribedEvents(subscribedEvents)
            .setTimestamp(timestamp)
            .build()
}
