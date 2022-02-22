package com.kietyo.multiplayer.gamelogic.model

import kotlinx.serialization.Serializable

@Serializable
sealed class PacketData


@Serializable
data class Packet(
    val data: PacketData,
    val creationTimeMillis: Long = getCurrentTimeMillis()
)

@Serializable
data class Player(val id: Int, var x: Double, var y: Double) : PacketData()

@Serializable
data class PlayerRemoved(val id: Int) : PacketData()