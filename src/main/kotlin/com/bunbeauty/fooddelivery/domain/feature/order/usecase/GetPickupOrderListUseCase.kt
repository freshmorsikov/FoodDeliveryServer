package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.domain.feature.order.model.LightOrder

class GetPickupOrderListUseCase(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(
        cafeUuid: String,
        limitTime: Long
    ): List<LightOrder> {
        val order = orderRepository.getLightPickupOrder(
            cafeUuid = cafeUuid,
            limitTime = limitTime
        )

        return order
    }
}
