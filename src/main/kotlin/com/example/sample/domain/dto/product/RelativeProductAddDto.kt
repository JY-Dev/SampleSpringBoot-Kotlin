package com.example.sample.domain.dto.product

data class RelativeProductAddDto(
    val targetProductId: Long,
    val resultProductId: Long,
    val score: Int
)
