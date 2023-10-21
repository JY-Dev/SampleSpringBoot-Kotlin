package com.example.sample.domain.dto.product

data class ProductUpdateDto(
    val name: String?,
    val image: String?,
    val url: String?,
    val originalPrice: Int?,
    val salePrice: Int?,
)
