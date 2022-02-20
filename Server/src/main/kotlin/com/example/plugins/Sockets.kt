package com.example.plugins

import com.example.PlayerConnection
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import java.time.*
import io.ktor.application.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(120)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val json = Json

    val playerConnections = Collections.synchronizedList<PlayerConnection>(LinkedList())

    routing {
        webSocket("/game") {
            val playerConnection = PlayerConnection(this)
            playerConnections += playerConnection
            println("Added player connection: $playerConnection")
            try {
                send("You are connected! There are ${playerConnections.size} users here.")
                send(json.encodeToString(playerConnection.player))
                for(frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()
                            outgoing.send(Frame.Text("YOU SAID: $text"))
                            if (text.equals("bye", ignoreCase = true)) {
                                close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                            }
                        }
                        is Frame.Binary -> TODO()
                        is Frame.Close -> TODO()
                        is Frame.Ping -> TODO()
                        is Frame.Pong -> TODO()
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                playerConnections -= playerConnection
                println("Removing $playerConnection")
                playerConnections.forEach {
                    it.session.send("${playerConnection} has left the room")
                }
            }

        }
    }
}
