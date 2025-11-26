package dev.httpmarco.polocloud.shared.module

/**
 * Represents a module in the PoloCloud system.
 *
 * Modules can be enabled and disabled, and should implement the lifecycle methods
 * to initialize or clean up resources as needed.
 */
interface PolocloudModule {

    /**
     * Called when the module is enabled.
     *
     * Implement this method to initialize resources or register services/events.
     */
    fun onEnable()

    /**
     * Called when the module is disabled.
     *
     * Implement this method to clean up resources or unregister services/events.
     */
    fun onDisable()
}
