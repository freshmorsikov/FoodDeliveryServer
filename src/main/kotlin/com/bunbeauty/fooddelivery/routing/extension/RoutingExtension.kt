package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.auth.JwtUser
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.error.ExceptionWithCode
import com.bunbeauty.fooddelivery.domain.toListWrapper
import com.bunbeauty.fooddelivery.routing.model.BodyRequest
import com.bunbeauty.fooddelivery.routing.model.Request
import com.bunbeauty.fooddelivery.util.fullMessage
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.authentication
import io.ktor.server.plugins.origin
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext
import java.sql.DriverManager.println

suspend inline fun RoutingContext.safely(block: () -> Unit) {
    try {
        block()
    } catch (exception: Exception) {
        when (exception) {
            is ExceptionWithCode -> {
                call.respond(
                    HttpStatusCode(
                        value = exception.code,
                        description = exception.message
                    ),
                    exception.fullMessage
                )
            }

            else -> {
                call.respondBad("Exception: ${exception.fullMessage}")
            }
        }

        println("Exception: ${exception.fullMessage}")
        exception.printStackTrace()
    }
}

suspend inline fun <reified R : Any> RoutingContext.getResult(block: () -> R) {
    safely {
        val result = block()
        call.respondOk(result)
    }
}

suspend inline fun <reified R : Any> RoutingContext.getListResult(block: () -> List<R>) {
    safely {
        val listResult = block()
        call.respondOk(listResult.toListWrapper())
    }
}

suspend inline fun RoutingContext.checkRights(
    block: (Request) -> Unit,
    checkBlock: (JwtUser) -> Boolean
) {
    safely {
        val jwtUser = call.authentication.principal() as? JwtUser
        if (jwtUser == null) {
            call.respond(HttpStatusCode.Unauthorized)
        } else {
            if (checkBlock(jwtUser)) {
                block(Request(jwtUser = jwtUser))
            } else {
                call.respond(HttpStatusCode.Forbidden)
            }
        }
    }
}

suspend inline fun <reified B, reified R : Any> RoutingContext.withBody(block: (B) -> R) {
    safely {
        val bodyModel: B = call.receive()
        val result = block(bodyModel)
        call.respondOk(model = result)
    }
}

suspend inline fun <reified B, reified R : Any> RoutingContext.handleRequestWithBody(
    request: Request,
    block: (BodyRequest<B>) -> R
) {
    val body: B = call.receive()
    val result = block(
        BodyRequest(
            request = request,
            body = body
        )
    )
    if (result == Unit) {
        call.respondOk()
    } else {
        call.respondOk(result)
    }
}

suspend inline fun <reified T : Any> RoutingContext.respond(block: () -> T) {
    call.respond(block())
}

suspend inline fun RoutingContext.deleteByUserUuid(
    request: Request,
    deleteBlock: (String) -> Unit
) {
    deleteBlock(request.jwtUser.uuid)
    call.respondNoContent()
}

suspend inline fun RoutingContext.deleteByUuid(
    deleteBlock: (String) -> Unit
) {
    val uuid = call.getParameter(UUID_PARAMETER)
    deleteBlock(uuid)
    call.respondNoContent()
}

val RoutingContext.clientIp: String
    get() = call.request.origin.remoteAddress

suspend inline fun ApplicationCall.respondOk() {
    respond(HttpStatusCode.OK)
}

suspend inline fun <reified T : Any> ApplicationCall.respondOk(model: T) {
    respond(HttpStatusCode.OK, model)
}

suspend inline fun ApplicationCall.respondBad(message: String) {
    respond(HttpStatusCode.BadRequest, message)
}

suspend inline fun ApplicationCall.respondNotFound() {
    respond(HttpStatusCode.NotFound)
}

suspend inline fun ApplicationCall.respondNoContent() {
    respond(HttpStatusCode.NoContent)
}
