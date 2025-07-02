package com.bunbeauty.fooddelivery.data.features.order.mapper

import com.bunbeauty.fooddelivery.data.entity.order.OrderEntity
import com.bunbeauty.fooddelivery.data.features.cafe.mapper.mapCafeEntityToCafeWithCity
import com.bunbeauty.fooddelivery.data.features.clientuser.mapper.mapClientUserEntityToLight
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyWithCafesEntity
import com.bunbeauty.fooddelivery.data.table.CityTable
import com.bunbeauty.fooddelivery.data.table.order.OrderTable
import com.bunbeauty.fooddelivery.domain.feature.order.model.LightOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import org.jetbrains.exposed.sql.ResultRow

val mapOrderEntity: OrderEntity.() -> Order = {
    Order(
        uuid = uuid,
        time = time,
        isDelivery = isDelivery,
        code = code,
        addressDescription = addressDescription,
        addressStreet = addressStreet,
        addressHouse = addressHouse,
        addressFlat = addressFlat,
        addressEntrance = addressEntrance,
        addressFloor = addressFloor,
        addressComment = addressComment,
        comment = comment,
        deferredTime = deferredTime,
        status = status,
        deliveryCost = deliveryCost,
        paymentMethod = paymentMethod,
        percentDiscount = percentDiscount,
        cafeWithCity = cafe.mapCafeEntityToCafeWithCity(),
        companyWithCafes = company.mapCompanyWithCafesEntity(),
        clientUser = clientUser.mapClientUserEntityToLight(),
        orderProducts = oderProducts.map(mapOrderProductEntity)
    )
}

val mapOrderTableToLightOrder: ResultRow.() -> LightOrder = {
    LightOrder(
        uuid = this[OrderTable.id].value.toString(),
        code = this[OrderTable.code],
        status = this[OrderTable.status],
        time = this[OrderTable.time],
        timeZone = this[CityTable.timeZone],
        deferredTime = this[OrderTable.deferredTime],
        cafeUuid = this[OrderTable.cafe].value.toString()
    )
}
