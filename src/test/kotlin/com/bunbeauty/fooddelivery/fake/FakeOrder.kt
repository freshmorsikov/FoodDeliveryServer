package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithCity
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithZones
import com.bunbeauty.fooddelivery.domain.feature.city.City
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserLight
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.Workload

object FakeOrder {

    fun create(
        uuid: String = "",
        isDelivery: Boolean = false,
        status: String = "DELIVERED",
        deliveryCost: Int? = null,
        percentDiscount: Int? = null,
        clientUser: ClientUserLight = FakeClientUserLight.create(
            uuid = "",
            phoneNumber = "",
            email = ""
        ),
        code: String = "",
        orderProductList: List<OrderProduct> = emptyList()
    ): Order {
        return Order(
            uuid = uuid,
            time = 0,
            isDelivery = isDelivery,
            code = code,
            addressDescription = "",
            addressStreet = "",
            addressHouse = "",
            addressFlat = "",
            addressEntrance = "",
            addressFloor = "",
            addressComment = "",
            comment = "",
            deferredTime = null,
            status = status,
            deliveryCost = deliveryCost,
            paymentMethod = "",
            percentDiscount = percentDiscount,
            cafeWithCity = CafeWithCity(
                cafeWithZones = CafeWithZones(
                    uuid = "",
                    fromTime = 0,
                    toTime = 0,
                    offset = 3,
                    phone = "",
                    latitude = 0.0,
                    longitude = 0.0,
                    address = "",
                    cityUuid = "",
                    isVisible = true,
                    workload = Workload.LOW,
                    workType = WorkType.CLOSED,
                    zones = emptyList(),
                    additionalUtensils = false
                ),
                city = City(
                    uuid = "",
                    name = "",
                    timeZone = "",
                    isVisible = true
                )
            ),
            companyWithCafes = FakeCompanyWithCafes.create(
                forFreeDelivery = 0,
                deliveryCost = 0
            ),
            clientUser = clientUser,
            orderProducts = orderProductList
        )
    }
}
