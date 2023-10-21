package com.example.sample.database.repository.product

import com.example.sample.database.dto.ProductSearchCondition
import com.example.sample.database.repository.product.jointype.ProductJoinType
import com.example.sample.domain.dto.product.ProductDto
import com.example.sample.domain.dto.product.RelativeProductDto
import com.example.sample.domain.entity.product.Product
import com.example.sample.domain.entity.product.QProduct.product
import com.example.sample.domain.entity.product.QRelativeProduct.relativeProduct
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ProductQueryDslRepository(
    private val queryFactory: JPAQueryFactory
) : ProductQueryRepository {

    override fun findProductEntity(productId: Long, vararg joinType: ProductJoinType): Product? =
        queryFactory.selectFrom(product)
            .setEntityJoin(joinType)
            .where(productIdBooleanExpression(productId))
            .fetchOne()

    override fun findProducts(condition: ProductSearchCondition): List<ProductDto> =
        queryFactory.select(productProjection())
            .from(product)
            .setProductSearchCondition(condition)
            .fetch()

    override fun findRelativeProducts(condition: ProductSearchCondition): List<RelativeProductDto> =
        queryFactory.select(relativeProductProjection())
            .from(relativeProduct)
            .leftJoin(relativeProduct.resultProduct, product)
            .setRelativeProductSearchCondition(condition)
            .fetch()

    private fun <T> JPAQuery<T>.setProductSearchCondition(condition: ProductSearchCondition): JPAQuery<T> {
        if (condition.productIds.isNotEmpty()) {
            where(product.id.`in`(condition.productIds))
        }
        return this
    }

    private fun <T> JPAQuery<T>.setRelativeProductSearchCondition(condition: ProductSearchCondition): JPAQuery<T> {
        if (condition.productIds.isNotEmpty()) {
            where(relativeProduct.targetProduct.id.`in`(condition.productIds))
        }
        return this
    }

    private fun productIdBooleanExpression(productId: Long) =
        product.id.eq(productId)

    private fun <T> JPAQuery<T>.setEntityJoin(type: Array<out ProductJoinType>): JPAQuery<T> {
        val isJoinALL = type.contains(ProductJoinType.ALL)
        if (isJoinALL || type.contains(ProductJoinType.RELATIVE_PRODUCT)) {
            this.leftJoin(product.relativeProducts, relativeProduct).fetchJoin()
            this.leftJoin(relativeProduct.resultProduct).fetchJoin()
        }
        return this
    }

    private fun productProjection() =
        Projections.constructor(
            ProductDto::class.java,
            product.id,
            product.name,
            product.image,
            product.url,
            product.originalPrice,
            product.salePrice
        )

    private fun relativeProductProjection() =
        Projections.constructor(
            RelativeProductDto::class.java,
            product.id,
            product.name,
            product.image,
            product.url,
            product.originalPrice,
            product.salePrice,
            relativeProduct.score,
            Expressions.numberTemplate(Integer::class.java, "rank() over(order by {0} desc)", relativeProduct.score)
        )
}