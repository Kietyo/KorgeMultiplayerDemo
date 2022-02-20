package com.example

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import com.example.plugins.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureHTTP()
        configureRouting()
        configureSockets()
        configureSecurity()
    }.start(wait = true)
}
