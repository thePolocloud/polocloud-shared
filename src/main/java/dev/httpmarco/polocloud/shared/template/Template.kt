package dev.httpmarco.polocloud.shared.template

import dev.httpmarco.polocloud.v1.templates.TemplateSnapshot
import kotlin.math.log10
import kotlin.math.pow

/**
 * Represents a template with a name and optional size.
 *
 * @property name the name of the template
 * @property size the human-readable size of the template, defaults to "unknown"
 */
open class Template(
    val name: String,
    private val size: String = "unknown"
) {

    companion object {

        /**
         * Creates a Template instance from a TemplateSnapshot.
         *
         * @param proto the snapshot to bind
         * @return a Template instance
         */
        fun from(proto: TemplateSnapshot): Template {
            return Template(name = proto.name, size = proto.size)
        }

        /**
         * Converts a list of TemplateSnapshot instances into a list of Template instances.
         *
         * @param list the list of snapshots to bind
         * @return a list of Template instances
         */
        fun fromSnapshotList(list: List<TemplateSnapshot>): List<Template> {
            return list.map { from(it) }
        }
    }

    /**
     * Converts this Template into a TemplateSnapshot.
     *
     * @return TemplateSnapshot representing this template
     */
    fun to(): TemplateSnapshot = TemplateSnapshot.newBuilder()
        .setName(name)
        .setSize(size())
        .build()

    /**
     * Converts a size in bytes to a human-readable string (B, KB, MB, GB, TB).
     *
     * @param bytes the size in bytes
     * @return human-readable size string
     */
    protected fun humanReadableSize(bytes: Long): String {
        if (bytes <= 0) return "empty"

        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(bytes.toDouble()) / log10(1024.0)).toInt()
        val humanValue = bytes / 1024.0.pow(digitGroups.toDouble())

        return String.format("%.1f %s", humanValue, units[digitGroups])
    }

    /**
     * Returns the size of the template.
     *
     * Can be overridden by subclasses to provide dynamic size calculation.
     *
     * @return human-readable size
     */
    open fun size(): String = size
}
