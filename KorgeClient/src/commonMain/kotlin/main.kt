import com.kietyo.multiplayer.gamelogic.model.*
import com.soywiz.klock.Date
import com.soywiz.korev.Key
import com.soywiz.korev.KeyEvent
import com.soywiz.korge.*
import com.soywiz.korge.animate.play
import com.soywiz.korge.component.KeyComponent
import com.soywiz.korge.component.docking.dockedTo
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Anchor
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.date.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.time.AbstractLongTimeSource
import kotlin.time.DurationUnit
import kotlin.time.measureTime
import kotlin.time.toDuration

val json = Json

class PlayerContainer(val id: Int) : Container() {
    val circle = circle(50.0, fill = Colors.TOMATO)
    val text = text(id.toString()) {
        scale = 2.0
        centerOn(circle)
    }

    fun updatePlayer(newPlayerState: Player) {
        require(id == newPlayerState.id)
        x = newPlayerState.x
        y = newPlayerState.y
    }

    fun toPlayer(): Player {
        return Player(id, x, y)
    }
}

class MovementKeys(
    override val view: PlayerContainer,
    val session: WebSocketSession
) : KeyComponent {
    val deltaX = 5.0
    val deltaY = 5.0

    override fun Views.onKeyEvent(event: KeyEvent) {
        when (event.key) {
            Key.W -> {
                view.y -= deltaY
            }
            Key.S -> {
                view.y += deltaY
            }
            Key.A -> {
                view.x -= deltaX
            }
            Key.D -> {
                view.x += deltaX
            }
        }
        launch {
            session.send(json.encodeToString(Packet(PacketType.PLAYER_UPDATE, view.toPlayer())))
        }
    }
}


suspend fun main() = Korge(width = 400, height = 400, bgcolor = Colors["#2b2b2b"]) {
    val client = HttpClient {
        install(WebSockets)
    }

    val scene = this

    val playerContainer = mutableMapOf<Int, PlayerContainer>()

    var myContainer: PlayerContainer
    var isFirstPlayer = true

//    https://korge-multiplayer-demo-fe4fq4lauq-uc.a.run.app/

    // Note: When using the gcloud server, make sure that `host` points to the URL.
    //  For example: host = "korge-multiplayer-demo-fe4fq4lauq-uc.a.run.app"
    //  Also, make sure that `port` is NOT SET
    client.webSocket(
        method = HttpMethod.Get,
        host = "127.0.0.1",
//        host = "korge-multiplayer-demo-fe4fq4lauq-uc.a.run.app",
        port = 8080,
        path = "/game"
    ) {
        // Incoming messages
        val incomingJob = launch {
            try {
                for (message in incoming) {
                    println("Received frame!")
                    when (message) {
                        is Frame.Binary -> TODO()
                        is Frame.Text -> {
                            val text = message.readText()
                            println("Got text: $text")

                            val packet = json.decodeFromStringOrNull<Packet>(text) ?: continue
                            println("Recieved packet: $packet")

                            val packetLatency = (getCurrentTimeMillis() - packet
                                .creationTimeMillis).toDuration(DurationUnit.MILLISECONDS)

                            println(
                                "Packet latency: $packetLatency"
                            )

                            when (packet.data) {
                                is Player -> {
                                    val player = packet.data as Player
                                    if (playerContainer.containsKey(player.id)) {
                                        playerContainer[player.id]!!.updatePlayer(player)
                                    } else {
                                        myContainer = PlayerContainer(player.id).addTo(scene)
                                        myContainer.updatePlayer(player)
                                        playerContainer[player.id] = myContainer
                                        if (isFirstPlayer) {
                                            println("Added first player container!")
                                            myContainer.addComponent(
                                                MovementKeys(
                                                    myContainer,
                                                    this@webSocket
                                                )
                                            )
                                            text("You are player: ${player.id}") {
                                                alignBottomToBottomOf(scene)
                                            }
                                            isFirstPlayer = false
                                        }
                                    }
                                }
                            }


                        }
                        is Frame.Close -> TODO()
                        is Frame.Ping -> TODO()
                        is Frame.Pong -> TODO()
                        else -> TODO()
                    }
                }
            } catch (e: Exception) {
                println("Error while receiving messages: $e")
            }
        }

        incomingJob.join()

        //        while(true) {}

        //        val messageOutputRoutine = launch { outputMessages() }
        //        val userInputRoutine = launch { inputMessages() }
        //
        //        userInputRoutine.join()
        //        messageOutputRoutine.cancelAndJoin()
    }
    //	client.close()
    //	println("Client closed! good bye!")
}

suspend fun DefaultClientWebSocketSession.outputMessages() {
    try {
        for (message in incoming) {
            val textFrame = message as? Frame.Text ?: continue
            println(textFrame.readText())
        }
    } catch (e: Exception) {
        println("Error while receiving messages: " + e.message)
    }
}

suspend fun DefaultClientWebSocketSession.inputMessages() {
    while (true) {
        println("Type your message: ")
        val message = readln()
        println("Got message: $message")
        if (message.equals("exit", true)) return
        try {
            send(message)
        } catch (e: Exception) {
            println("Error while sending: " + e.message)
            return
        }
    }
}