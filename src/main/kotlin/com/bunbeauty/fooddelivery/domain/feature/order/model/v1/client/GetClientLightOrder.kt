package com.bunbeauty.fooddelivery.domain.feature.order.model.v1.client

import kotlinx.serialization.Serializable

@Serializable
class GetClientLightOrder(
    val uuid: String,
    val code: String,
    val status: String,
    val time: Long,
    val timeZone: String
)
