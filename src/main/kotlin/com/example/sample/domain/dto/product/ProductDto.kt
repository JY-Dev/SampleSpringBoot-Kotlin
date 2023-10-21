package com.example.sample.domain.dto.product

data class ProductDto(
    val productId: Long,
    val productName: String,
    val productImage: String,
    val productUrl: String,
    val originalPrice: Int,
    val salePrice: Int,
)
