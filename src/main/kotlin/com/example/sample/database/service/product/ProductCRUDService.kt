package com.example.sample.database.service.product

import com.example.sample.database.repository.product.ProductCommandRepository
import com.example.sample.database.repository.product.ProductQueryRepository
import com.example.sample.database.repository.product.jointype.ProductJoinType
import com.example.sample.domain.dto.product.ProductCreateDto
import com.example.sample.domain.dto.product.ProductUpdateDto
import com.example.sample.domain.entity.product.Product
import com.example.sample.domain.entity.product.get
import com.example.sample.domain.entity.product.toDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductCRUDService(
    private val productCommandRepository: ProductCommandRepository,
    private val productQueryRepository: ProductQueryRepository
) {

    fun registerProduct(dto: ProductCreateDto) {

        val product = Product.create(dto)

        productCommandRepository.save(product)
    }

    @Transactional
    fun updateProduct(dto: ProductUpdateDto, productId: Long) {

        val product = productQueryRepository.findProductEntity(
            productId = productId,
            joinType = arrayOf(ProductJoinType.NONE)
        ).get()

        product.updateProduct(dto)
    }

    @Transactional
    fun deleteProduct(productId: Long) {

        val product = productQueryRepository.findProductEntity(
            productId = productId,
            joinType = arrayOf(ProductJoinType.RELATIVE_PRODUCT)
        ).get()

        productCommandRepository.delete(product)
    }

    fun getProduct(productId: Long) =
        productQueryRepository.findProductEntity(
            productId = productId,
            joinType = arrayOf(ProductJoinType.NONE)
        ).get().toDto()

    fun getProducts() =
        productCommandRepository.findAll()
            .map {
                it.toDto()
            }
}