package BottomupDDD

import BottomupDDD.domain.user.*
import BottomupDDD.domain.application.*

import BottomupDDD.infrastructure.sqlite.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

import BottomupDDD.infrastructure.inMemory.*

import BottomupDDD.web.controller.*
import BottomupDDD.web.view.*

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.html.*

fun main(args: Array<String>) {
    val connect = {
        val db = Database.connect("jdbc:sqlite:/tmp/bottomup_ddd.db", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        db
    }
    val userRepository = SQLiteUserRepository(connect)
    // val userRepository = InMemoryUserRepository()
    val userService = UserApplicationService(userRepository)

    val server = embeddedServer(Netty, port = 8080) {
        install(Locations)
        routing {
            get("/") {
                call.respondHtmlTemplate(HomeTemplate()) {}
            }

            userRoute(userService)
        }
    }
    server.start(wait = true)
}
