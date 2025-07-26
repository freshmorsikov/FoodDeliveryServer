package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.routing.model.BodyRequest
import com.bunbeauty.fooddelivery.routing.model.Request
import io.ktor.server.routing.RoutingContext

suspend inline fun RoutingContext.client(block: (Request) -> Unit) {
    checkRights(block) { jwtUser ->
        jwtUser.isClient()
    }
}

suspend inline fun <reified R : Any> RoutingContext.clientGetResult(block: (Request) -> R) {
    client { request ->
        getResult {
            block(request)
        }
    }
}

suspend inline fun <reified R : Any> RoutingContext.clientGetListResult(
    block: (Request) -> List<R>
) {
    client { request ->
        getListResult {
            block(request)
        }
    }
}

suspend inline fun <reified B, reified R : Any> RoutingContext.clientWithBody(
    block: (BodyRequest<B>) -> R
) {
    client { request ->
        handleRequestWithBody(
            request = request,
            block = block
        )
    }
}
