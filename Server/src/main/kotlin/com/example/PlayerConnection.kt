package com.example

import com.kietyo.multiplayer.gamelogic.model.Player
import io.ktor.http.cio.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class PlayerConnection(val session: DefaultWebSocketSession) {
    companion object {
        val ID = AtomicInteger()
    }

    val id = ID.getAndIncrement()

    var player = Player(id.toLong(), 0.0, 0.0)

    override fun toString(): String {
        return "PlayerConnection(id=$id)"
    }
}