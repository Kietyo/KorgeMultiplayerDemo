package com.kietyo.multiplayer.gamelogic.model

import kotlinx.serialization.Serializable

enum class PacketType {
    PLAYER_UPDATE,
    PLAYER_EXIT
}

@Serializable
sealed class PacketData


@Serializable
data class Packet(
    val type: PacketType,
    val data: PacketData,
    val creationTimeMillis: Long = getCurrentTimeMillis()
)

@Serializable
data class Player(val id: Int, var x: Double, var y: Double) : PacketData()