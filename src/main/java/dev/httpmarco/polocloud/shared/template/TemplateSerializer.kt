package dev.httpmarco.polocloud.shared.template

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

/**
 * JSON serializer and deserializer for the [Template] class.
 *
 * Serializes a Template to its name as a JSON string and deserializes
 * a JSON string back to a Template instance.
 */
class TemplateSerializer : JsonSerializer<Template>, JsonDeserializer<Template> {

    /**
     * Serializes a [Template] into its JSON representation (the template's name as a string).
     *
     * @param src the Template to serialize
     * @param typeOfSrc the actual type of the source object (ignored)
     * @param context context for serialization (ignored)
     * @return JSON element representing the template's name
     */
    override fun serialize(
        src: Template,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonPrimitive(src.name)

    /**
     * Deserializes a JSON element into a [Template] instance.
     *
     * @param json the JSON element containing the template name
     * @param typeOfT the type of the Object to deserialize to (ignored)
     * @param context context for deserialization (ignored)
     * @return Template instance with the given name
     */
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Template = Template(json.asJsonPrimitive.asString)
}
