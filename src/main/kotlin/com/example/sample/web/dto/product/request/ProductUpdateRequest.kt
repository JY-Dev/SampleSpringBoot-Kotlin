package com.example.sample.web.dto.product.request

import com.example.sample.domain.dto.product.ProductUpdateDto

data class ProductUpdateRequest(
    val name: String?,
    val image: String?,
    val url: String?,
    val originalPrice: Int?,
    val salePrice: Int?,
) {
    fun toDomain(): ProductUpdateDto =
        ProductUpdateDto(
            name = this.name,
            image = this.image,
            url = this.url,
            originalPrice = this.originalPrice,
            salePrice = this.salePrice
        )
}
