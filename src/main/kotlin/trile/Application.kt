package trile

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import trile.plugins.configureRouting
import trile.plugins.configureSerialization
import trile.plugins.configureSpringContext

fun main() {
  embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
    .start(wait = true)
}

fun Application.module() {
  configureSerialization()
  configureRouting()
  configureSpringContext()
}
