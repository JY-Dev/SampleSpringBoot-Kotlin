package com.example.sample.web.dto.product.request

import com.example.sample.domain.dto.product.RelativeProductUpdateDto

data class RelativeProductUpdateRequest(
    val targetProductId: Long,
    val resultProductId: Long,
    val changeResultProductId: Long,
    val score: Int
) {
    fun toDomain(): RelativeProductUpdateDto =
        RelativeProductUpdateDto(
            targetProductId = this.targetProductId,
            resultProductId = this.resultProductId,
            changeResultProductId = this.changeResultProductId,
            score = this.score
        )
}
