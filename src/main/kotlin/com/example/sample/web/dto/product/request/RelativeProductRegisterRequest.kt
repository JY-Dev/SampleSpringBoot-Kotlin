package com.example.sample.web.dto.product.request

import com.example.sample.domain.dto.product.RelativeProductAddDto

data class RelativeProductRegisterRequest(
    val resultProductId: Long,
    val targetProductId: Long,
    val score: Int
) {
    fun toDomain(): RelativeProductAddDto =
        RelativeProductAddDto(
            resultProductId = this.resultProductId,
            targetProductId = this.targetProductId,
            score = this.score
        )
}
