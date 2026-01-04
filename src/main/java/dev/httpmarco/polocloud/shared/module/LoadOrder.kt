package dev.httpmarco.polocloud.shared.module

/**
 * Defines when a module should be loaded.
 */
enum class LoadOrder {
    /**
     * Load during initial startup (default)
     */
    STARTUP,

    /**
     * Load after all STARTUP modules are loaded.
     */
    POST_STARTUP,

    /**
     * Load last, after everything else is ready.
     */
    LATE
}