package BottomupDDD.web.controller

import BottomupDDD.domain.user.*
import BottomupDDD.domain.application.*

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

fun Route.userRoute(service: UserApplicationService) {
    get("/user") {
        runCatching { service.getUserList() }
            .onSuccess { call.respondHtmlTemplate(UserIndexTemplate(it)) {} }
            .returnErrorOnFailure(call)
    }

    get("/user/newuser") {
        call.respondHtmlTemplate(NewUserTemplate()) {}
    }

    post("/user/register") {
        runCatching { call.receiveParameters().toUser() }
            .mapCatching { service.registerUser(it) }
            .onSuccess { call.respondRedirect("/user") }
            .returnErrorOnFailure(call)
    }

    @Location("/user/detail/{id}") data class UserDetail(val id: String)
    get<UserDetail> { param ->
        runCatching { service.getUserInfo(UserId(param.id)) }
            .onSuccess { call.respondHtmlTemplate(UserDetailTemplate(it)) {} }
            .returnErrorOnFailure(call)
    }

    @Location("/user/edit/{id}") data class UserEdit(val id: String)
    get<UserEdit> { param ->
        runCatching { service.getUserInfo(UserId(param.id)) }
            .onSuccess { call.respondHtmlTemplate(UserEditTemplate(it)) {} }
            .returnErrorOnFailure(call)
    }

    post("/user/update") {
        runCatching { call.receiveParameters().toUserById() }
            .mapCatching { service.changeUserInfo(it); it }
            .onSuccess { call.respondRedirect("/user/detail/${it.id.value}") }
            .returnErrorOnFailure(call)
    }

    @Location("/user/delete/{id}") data class UserDelete(val id: String)
    get<UserDelete> { param ->
        runCatching { service.getUserInfo(UserId(param.id)) }
            .onSuccess { call.respondHtmlTemplate(UserDeleteTemplate(it)) {} }
            .returnErrorOnFailure(call)
    }

    post("/user/deleteuser") {
         runCatching { call.receiveParameters()["id"]!! }
            .mapCatching { service.removeUser(UserId(it)) }
            .onSuccess { call.respondRedirect("/user") }
            .returnErrorOnFailure(call)
   }
}

fun Parameters.toUser() = User(
    UserName(this["username"]!!)
    , FullName(this["firstname"]!!, this["familyname"]!!))

fun Parameters.toUserById() = User(
    UserId(this["id"]!!), UserName(this["username"]!!)
    , FullName(this["firstname"]!!, this["familyname"]!!))

fun Throwable.toErrorResponse() = when (this) {
    is NullPointerException, is IllegalArgumentException ->
        Pair("Invalid inputs.", HttpStatusCode.BadRequest)
    is UserNameIsDuplicated ->
        Pair(toString(), HttpStatusCode.BadRequest)
    is UserIdIsDuplicated ->
        Pair(toString(), HttpStatusCode.InternalServerError)
    is UserIsNotFound ->
        Pair(toString(), HttpStatusCode.BadRequest)
    else -> Pair(toString(), HttpStatusCode.InternalServerError)
}

suspend fun <T> Result<T>.returnErrorOnFailure(call: ApplicationCall) = onFailure {
    val (message, statusCode) = it.toErrorResponse()
    call.respondText(message, status = statusCode)
}.let { Unit }
