package com.example.sample.database.repository.product

import com.example.sample.database.dto.ProductSearchCondition
import com.example.sample.database.repository.product.jointype.ProductJoinType
import com.example.sample.domain.dto.product.ProductDto
import com.example.sample.domain.dto.product.RelativeProductDto
import com.example.sample.domain.entity.product.Product

interface ProductQueryRepository {
    fun findProductEntity(productId: Long, vararg joinType: ProductJoinType): Product?
    fun findProducts(condition: ProductSearchCondition): List<ProductDto>
    fun findRelativeProducts(condition: ProductSearchCondition): List<RelativeProductDto>
}