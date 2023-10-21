package com.example.sample.domain.dto.product

data class RelativeProductDto(
    val productId: Long,
    val productName: String,
    val productImage: String,
    val productUrl: String,
    val originalPrice: Int,
    val salePrice: Int,
    val score: Int,
    val rank: Int
)
