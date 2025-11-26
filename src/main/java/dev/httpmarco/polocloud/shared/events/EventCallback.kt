package dev.httpmarco.polocloud.shared.events

fun interface EventCallback<T> {
    fun call(event: T)
}