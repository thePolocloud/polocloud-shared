package dev.httpmarco.polocloud.shared.service

import dev.httpmarco.polocloud.shared.template.Template

/**
 * Configuration builder for booting shared services.
 *
 * Allows setting memory limits, templates, excluded templates, and custom properties.
 */
class SharedBootConfiguration {

    private var minMemory: Int? = null
    private var maxMemory: Int? = null
    private val templates = mutableListOf<Template>()
    private val excludedTemplates = mutableListOf<Template>()
    private val properties = mutableMapOf<String, String>()

    /** Returns the minimum memory configured, or null if not set. */
    fun minMemory(): Int? = minMemory

    /** Returns the maximum memory configured, or null if not set. */
    fun maxMemory(): Int? = maxMemory

    /** Returns the list of included templates. */
    fun templates(): List<Template> = templates

    /** Returns the list of excluded templates. */
    fun excludedTemplates(): List<Template> = excludedTemplates

    /** Returns the map of custom properties. */
    fun properties(): Map<String, String> = properties

    /** Sets the minimum memory for the configuration. */
    fun withMinMemory(minMemory: Int): SharedBootConfiguration {
        this.minMemory = minMemory
        return this
    }

    /** Sets the maximum memory for the configuration. */
    fun withMaxMemory(maxMemory: Int): SharedBootConfiguration {
        this.maxMemory = maxMemory
        return this
    }

    /** Adds a template to the included templates list. */
    fun withTemplate(template: Template): SharedBootConfiguration {
        this.templates += template
        return this
    }

    /** Adds multiple templates to the included templates list. */
    fun withTemplates(vararg templates: Template): SharedBootConfiguration {
        this.templates += templates
        return this
    }

    /** Adds a template to the excluded templates list. */
    fun withExcludedTemplate(template: Template): SharedBootConfiguration {
        this.excludedTemplates += template
        return this
    }

    /** Adds multiple templates to the excluded templates list. */
    fun withExcludedTemplates(vararg templates: Template): SharedBootConfiguration {
        this.excludedTemplates += templates
        return this
    }

    /** Adds a custom property key-value pair to the configuration. */
    fun withProperty(key: String, value: String): SharedBootConfiguration {
        this.properties[key] = value
        return this
    }
}
