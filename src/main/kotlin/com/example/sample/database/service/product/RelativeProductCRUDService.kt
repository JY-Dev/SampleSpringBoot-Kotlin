package com.example.sample.database.service.product

import com.example.sample.database.dto.ProductSearchCondition
import com.example.sample.database.repository.product.ProductQueryRepository
import com.example.sample.database.repository.product.jointype.ProductJoinType
import com.example.sample.domain.dto.product.CombinedProductAndRelativeDto
import com.example.sample.domain.dto.product.RelativeProductAddDto
import com.example.sample.domain.dto.product.RelativeProductRemoveDto
import com.example.sample.domain.dto.product.RelativeProductUpdateDto
import com.example.sample.domain.entity.product.RelativeProduct
import com.example.sample.domain.entity.product.get
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RelativeProductCRUDService(
    private val productQueryRepository: ProductQueryRepository
) {

    @Transactional
    fun addRelativeProduct(dto: RelativeProductAddDto) {

        RelativeProduct.validateProductIdMismatch(dto.resultProductId, dto.targetProductId)

        val targetProduct = productQueryRepository.findProductEntity(
            productId = dto.targetProductId,
            joinType = arrayOf(ProductJoinType.RELATIVE_PRODUCT)
        ).get()

        val resultProduct = productQueryRepository.findProductEntity(
            productId = dto.resultProductId,
            joinType = arrayOf(ProductJoinType.NONE)
        ).get()

        val relativeProduct = RelativeProduct.create(resultProduct, targetProduct, dto)
        targetProduct.addRelativeProduct(relativeProduct)
    }

    @Transactional
    fun removeRelativeProduct(dto: RelativeProductRemoveDto) {

        val targetProduct = productQueryRepository.findProductEntity(
            productId = dto.targetProductId,
            joinType = arrayOf(ProductJoinType.RELATIVE_PRODUCT)
        ).get()

        targetProduct.removeRelativeProduct(dto.resultProductId)
    }

    @Transactional
    fun updateRelativeProduct(dto: RelativeProductUpdateDto) {

        val targetProduct = productQueryRepository.findProductEntity(
            productId = dto.targetProductId,
            joinType = arrayOf(ProductJoinType.RELATIVE_PRODUCT)
        ).get()

        targetProduct.updateRelativeProduct(dto) { productId ->
            productQueryRepository.findProductEntity(productId).get()
        }
    }

    fun getCombinedProductAndRelative(dto: ProductSearchCondition): CombinedProductAndRelativeDto {
        val targetProducts = productQueryRepository.findProducts(dto)
        val relativeProducts = productQueryRepository.findRelativeProducts(dto)
        return CombinedProductAndRelativeDto(targetProducts, relativeProducts)
    }

}