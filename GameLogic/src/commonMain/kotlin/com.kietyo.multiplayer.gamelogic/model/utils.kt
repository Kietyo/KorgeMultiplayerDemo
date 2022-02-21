package com.kietyo.multiplayer.gamelogic.model

import com.soywiz.klock.DateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

public inline fun <reified T> Json.decodeFromStringOrNull(string: String): T? {
    return try {
        decodeFromString(serializersModule.serializer(), string)
    } catch (e: Exception) {
        null
    }
}

fun getCurrentTimeMillis() = DateTime.nowUnixLong()