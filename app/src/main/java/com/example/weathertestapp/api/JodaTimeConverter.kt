package com.example.weathertestapp.api

import com.google.gson.*
import org.joda.time.DateTime
import java.lang.reflect.Type
import java.util.*

class DateTimeTypeConverter : JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
    override fun serialize(src: DateTime, srcType: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.toString())
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): DateTime {
        return try {
            DateTime(json.asString)
        } catch (e: IllegalArgumentException) {
            // May be it came in formatted as a java.util.Date, so try that
            val date = context.deserialize<Date>(json, Date::class.java)
            DateTime(date)
        }

    }
}