package dev.httpmarco.polocloud.shared.module

/**
 * Represents metadata information for a module in the system.
 *
 * @property id the unique identifier of the module
 * @property name the human-readable name of the module
 * @property description a brief description of what the module does
 * @property author the author of the module
 * @property main the fully qualified main class of the module
 */
data class ModuleMetadata(
    val id: String,
    val name: String,
    val description: String,
    val author: String,
    val main: String
)
