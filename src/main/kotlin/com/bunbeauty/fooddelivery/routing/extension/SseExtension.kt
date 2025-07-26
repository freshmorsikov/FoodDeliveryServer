package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.auth.JwtUser
import com.bunbeauty.fooddelivery.routing.model.Request
import io.ktor.server.auth.authentication
import io.ktor.server.sse.ServerSSESession
import io.ktor.websocket.close
import kotlinx.coroutines.DelicateCoroutinesApi
import java.sql.DriverManager.println

suspend inline fun ServerSSESession.clientSse(
    block: (Request) -> Unit,
    crossinline closeBlock: (Request) -> Unit
) {
    println("clientSse")
    sse(block = block, closeBlock = closeBlock) { jwtUser ->
        jwtUser.isClient()
    }
}

suspend inline fun ServerSSESession.managerSse(
    block: (Request) -> Unit,
    crossinline onSocketClose: (Request) -> Unit
) {
    println("managerSse")
    sse(block = block, closeBlock = onSocketClose) { jwtUser ->
        jwtUser.isManager()
    }
}

@OptIn(DelicateCoroutinesApi::class)
suspend inline fun ServerSSESession.sse(
    block: (Request) -> Unit,
    crossinline closeBlock: (Request) -> Unit,
    checkAccess: (JwtUser) -> Boolean
) {
    val jwtUser = call.authentication.principal() as? JwtUser
    if (jwtUser == null) {
        close()
        return
    }

    if (!checkAccess(jwtUser)) {
        close()
        return
    }

    val request = Request(jwtUser)
    try {
        block(request)
    } finally {
        closeBlock(request)
    }
}
