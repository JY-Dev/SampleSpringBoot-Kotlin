package com.example.sample.web.dto.product.request

import com.example.sample.domain.dto.product.ProductCreateDto

data class ProductRegisterRequest(
    val name: String,
    val image: String,
    val url: String,
    val originalPrice: Int,
    val salePrice: Int,
) {
    fun toDomain(): ProductCreateDto =
        ProductCreateDto(
            name = this.name,
            image = this.image,
            url = this.url,
            originalPrice = this.originalPrice,
            salePrice = this.salePrice
        )
}
