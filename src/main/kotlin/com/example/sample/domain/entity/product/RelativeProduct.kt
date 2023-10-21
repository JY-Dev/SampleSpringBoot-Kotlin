package com.example.sample.domain.entity.product

import com.example.sample.domain.dto.product.RelativeProductAddDto
import com.example.sample.domain.entity.base.BaseEntity
import com.example.sample.domain.exception.InValidException
import com.example.sample.domain.exception.NotFoundException
import jakarta.persistence.*

@Entity
@Table(
    indexes = [Index(
        name = "idx__relative__product_score",
        columnList = "score"
    )]
)
class RelativeProduct(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_product_id", referencedColumnName = "product_id")
    val targetProduct: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_product_id", referencedColumnName = "product_id")
    val resultProduct: Product,

    val score: Int,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "relative_product_id")
    var id: Long? = null
) : BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RelativeProduct) return false

        return resultProduct == other.resultProduct &&
                targetProduct == other.targetProduct
    }

    override fun hashCode(): Int {
        var result = resultProduct.hashCode()
        result = 31 * result + targetProduct.hashCode()
        return result
    }

    companion object {
        fun validateProductIdMismatch(resultProductId: Long, targetProductId: Long) {
            if (resultProductId == targetProductId) {
                throw InValidException(InValidException.DEFAULT)
            }
        }

        fun create(resultProduct: Product, targetProduct: Product, dto: RelativeProductAddDto) =
            RelativeProduct(
                targetProduct = targetProduct,
                resultProduct = resultProduct,
                score = dto.score
            )
    }
}

fun RelativeProduct?.get(): RelativeProduct =
    this ?: throw NotFoundException(NotFoundException.RELATIVE_PRODUCT)