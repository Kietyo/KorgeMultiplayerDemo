package com.kietyo.multiplayer.gamelogic.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(val id: Int, var x: Double, var y: Double)
