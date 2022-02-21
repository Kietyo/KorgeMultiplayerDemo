package com.example.plugins

import com.example.PlayerConnection
import com.kietyo.multiplayer.gamelogic.model.Packet
import com.kietyo.multiplayer.gamelogic.model.PacketType
import com.kietyo.multiplayer.gamelogic.model.Player
import com.kietyo.multiplayer.gamelogic.model.decodeFromStringOrNull
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import java.time.*
import io.ktor.application.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(120)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val json = Json

    val playerConnections = ConcurrentHashMap<Int, PlayerConnection>()

    routing {
        webSocket("/game") {
            val playerConnection = PlayerConnection(this)
            playerConnections[playerConnection.id] = playerConnection
            println("Added player connection: $playerConnection")
            try {
                send("You are connected! There are ${playerConnections.size} users here.")
                val currentPlayerJson = json.encodeToString(
                    Packet(
                        PacketType.PLAYER_UPDATE,
                        playerConnection.player
                    )
                )
                send(currentPlayerJson)
                playerConnections.asSequence().filter {
                    it.key != playerConnection.id
                }.forEach {
                    it.value.session.send(currentPlayerJson)
                    send(json.encodeToString(
                        Packet(PacketType.PLAYER_UPDATE, it.value.player)
                    ))
                }
                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()
                            val packet = json.decodeFromStringOrNull<Packet>(text)
                            if (packet == null) {
                                println("Got frame: $text")
                                continue
                            }

                            when (packet.data) {
                                is Player -> {
                                    playerConnection.player = packet.data as Player
                                    playerConnections.asSequence().filter {
                                        it.key != playerConnection.id
                                    }.forEach {
                                        it.value.session.send(text)
                                    }
                                }
                            }

//                            outgoing.send(Frame.Text("YOU SAID: $text"))
//                            if (text.equals("bye", ignoreCase = true)) {
//                                close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
//                            }
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
                playerConnections.remove(playerConnection.id)
                println("Removing $playerConnection")
                playerConnections.forEach {
                    it.value.session.send("${playerConnection} has left the room")
                }
            }

        }
    }
}
