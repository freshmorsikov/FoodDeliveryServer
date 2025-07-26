package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.routing.model.BodyRequest
import com.bunbeauty.fooddelivery.routing.model.Request
import io.ktor.server.routing.RoutingContext

suspend inline fun RoutingContext.admin(block: (Request) -> Unit) {
    checkRights(block) { jwtUser ->
        jwtUser.isAdmin()
    }
}

suspend inline fun <reified R : Any> RoutingContext.adminGetResult(block: (Request) -> R) {
    admin { request ->
        getResult {
            block(request)
        }
    }
}

suspend inline fun <reified R : Any> RoutingContext.getAdminWithListResult(block: (Request) -> List<R>) {
    admin { request ->
        getListResult {
            block(request)
        }
    }
}

suspend inline fun <reified B, reified R : Any> RoutingContext.adminWithBody(
    block: (BodyRequest<B>) -> R
) {
    admin { request ->
        handleRequestWithBody(
            request = request,
            block = block
        )
    }
}
