import com.soywiz.klock.seconds
import com.soywiz.korge.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.*
import com.soywiz.korio.async.runBlockingNoJs
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.interpolation.Easing
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class PlayerContainer(val id: Int) : Container() {
    val circle = circle(1.0)
}

suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {
    val client = HttpClient {
        install(WebSockets)
    }

    val json = Json

    val playerContainers = mutableMapOf<Int, PlayerContainer>()

    client.webSocket(
        method = HttpMethod.Get,
        host = "127.0.0.1",
        port = 8080,
        path = "/game"
    ) {
        // Incoming messages
        launch {
            try {
                for (message in incoming) {
                    println("Received frame!")
                    when (message) {
                        is Frame.Binary -> TODO()
                        is Frame.Text -> {
                            println(message.readText())
                        }
                        is Frame.Close -> TODO()
                        is Frame.Ping -> TODO()
                        is Frame.Pong -> TODO()
                        else -> TODO()
                    }
                }
            } catch (e: Exception) {
                println("Error while receiving messages: " + e.message)
            }
        }
        launch {

        }

        while(true) {}

//        val messageOutputRoutine = launch { outputMessages() }
//        val userInputRoutine = launch { inputMessages() }
//
//        userInputRoutine.join()
//        messageOutputRoutine.cancelAndJoin()
    }

	client.close()
	println("Client closed! good bye!")
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