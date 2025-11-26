package dev.httpmarco.polocloud.shared.information

import dev.httpmarco.polocloud.v1.information.AggregateCloudInformationSnapshot

/**
 * Represents aggregated cloud information collected across multiple nodes or services.
 * This data class provides a simplified overview of system utilization at a specific point in time.
 *
 * @property timestamp The moment (in milliseconds since epoch) when this aggregated information was recorded.
 * @property avgCpu The average CPU usage across all participating nodes, expressed as a percentage (0–100).
 * @property avgRam The average RAM usage across all participating nodes, expressed in megabytes.
 */
data class AggregateCloudInformation(
    val timestamp: Long,
    val avgCpu: Double,
    val avgRam: Double
) {

    companion object {

        /**
         * Creates an [AggregateCloudInformation] instance from a protobuf
         * [AggregateCloudInformationSnapshot].
         *
         * @param snapshot The protobuf snapshot containing aggregated cloud metrics.
         * @return A new [AggregateCloudInformation] instance mapped from the snapshot.
         */
        fun from(snapshot: AggregateCloudInformationSnapshot): AggregateCloudInformation {
            return AggregateCloudInformation(
                snapshot.timestamp,
                snapshot.avgCpu,
                snapshot.avgRam
            )
        }
    }

    /**
     * Converts this aggregated cloud information instance into a protobuf
     * [AggregateCloudInformationSnapshot].
     *
     * @return A protobuf snapshot representing this aggregated cloud information.
     */
    fun to(): AggregateCloudInformationSnapshot {
        return AggregateCloudInformationSnapshot.newBuilder()
            .setTimestamp(timestamp)
            .setAvgCpu(avgCpu)
            .setAvgRam(avgRam)
            .build()
    }
}