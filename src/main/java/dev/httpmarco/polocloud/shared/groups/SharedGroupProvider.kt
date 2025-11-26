package dev.httpmarco.polocloud.shared.groups

import dev.httpmarco.polocloud.dev.httpmarco.polocloud.shared.groups.Group
import java.util.concurrent.CompletableFuture

/**
 * Provides access and management operations for shared groups.
 *
 * @param G the type of group managed by this provider
 */
interface SharedGroupProvider<G : Group> {

    /**
     * Returns a list of all groups.
     */
    fun findAll(): List<G>

    /**
     * Returns a CompletableFuture that will complete with a list of all groups.
     */
    fun findAllAsync(): CompletableFuture<List<G>>

    /**
     * Finds a group by its name.
     *
     * @param name the name of the group
     * @return the group if found, null otherwise
     */
    fun find(name: String): G?

    /**
     * Asynchronously finds a group by its name.
     *
     * @param name the name of the group
     * @return a CompletableFuture completing with the group if found, or null
     */
    fun findAsync(name: String): CompletableFuture<G?>

    /**
     * Creates a new group.
     *
     * @param group the group to create
     * @return the created group, or null if creation failed
     */
    fun create(group: G): G?

    /**
     * Asynchronously creates a new group.
     *
     * @param group the group to create
     * @return a CompletableFuture completing with the created group, or null if creation failed
     */
    fun createAsync(group: G): CompletableFuture<G?>

    /**
     * Updates an existing group.
     *
     * @param group the group to update
     * @return the updated group, or null if update failed
     */
    fun update(group: G): G?

    /**
     * Asynchronously updates an existing group.
     *
     * @param group the group to update
     * @return a CompletableFuture completing with the updated group, or null if update failed
     */
    fun updateAsync(group: G): CompletableFuture<G?>

    /**
     * Deletes a group by its name.
     *
     * @param name the name of the group
     * @return the deleted group if it existed, null otherwise
     */
    fun delete(name: String): G?

    /**
     * Deletes the given group.
     *
     * @param group the group to delete
     */
    fun delete(group: G) {
        delete(group.name)
    }
}
