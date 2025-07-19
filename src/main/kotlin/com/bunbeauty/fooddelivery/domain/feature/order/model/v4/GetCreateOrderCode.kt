package com.bunbeauty.fooddelivery.domain.feature.order.model.v4

import kotlinx.serialization.Serializable

@Serializable
class GetCreateOrderCode(
    val code: String,
    val uuid: String
)
