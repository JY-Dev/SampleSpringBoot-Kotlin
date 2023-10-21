package com.example.sample.web.dto.product.request

import com.example.sample.domain.dto.product.RelativeProductRemoveDto

data class RelativeProductRemoveRequest(
    val resultProductId: Long,
    val targetProductId: Long
) {
    fun toDomain(): RelativeProductRemoveDto =
        RelativeProductRemoveDto(
            resultProductId = this.resultProductId,
            targetProductId = this.targetProductId
        )
}
