package com.example.sample.domain.dto.product

data class RelativeProductUpdateDto(
    val targetProductId: Long,
    val resultProductId: Long,
    val changeResultProductId: Long,
    val score: Int
)
