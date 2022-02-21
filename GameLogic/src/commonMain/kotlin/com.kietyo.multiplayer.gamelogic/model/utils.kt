package com.kietyo.multiplayer.gamelogic.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

public inline fun <reified T> Json.decodeFromStringOrNull(string: String): T? {
    return try {
        decodeFromString(serializersModule.serializer(), string)
    } catch (e: Exception) {
        null
    }
}