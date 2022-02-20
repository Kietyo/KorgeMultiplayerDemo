package com.kietyo.multiplayer.gamelogic.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerContainer(val players: MutableList<Player>)
