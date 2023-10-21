package com.example.sample.domain.dto.product

data class CombinedProductAndRelativeDto(
    val target: List<ProductDto>,
    val results: List<RelativeProductDto>
)
