package com.example.sample.domain.entity.product

import com.example.sample.domain.dto.product.ProductCreateDto
import com.example.sample.domain.dto.product.ProductDto
import com.example.sample.domain.dto.product.ProductUpdateDto
import com.example.sample.domain.dto.product.RelativeProductUpdateDto
import com.example.sample.domain.entity.base.BaseEntity
import com.example.sample.domain.exception.InValidException
import com.example.sample.domain.exception.NotFoundException
import jakarta.persistence.*

@Entity
class Product(

    @Column(name = "product_name")
    var name: String,

    @Column(name = "product_image")
    var image: String,

    @Column(name = "product_url")
    var url: String,

    var originalPrice: Int,

    var salePrice: Int,

    @OneToMany(
        mappedBy = "targetProduct",
        orphanRemoval = true,
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST]
    )
    val relativeProducts: MutableSet<RelativeProduct> = mutableSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    var id: Long? = null
) : BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Product) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    fun updateProduct(updateDto: ProductUpdateDto) {
        this.name = updateDto.name ?: this.name
        this.image = updateDto.image ?: this.image
        this.url = updateDto.url ?: this.url
        this.originalPrice = updateDto.originalPrice ?: this.originalPrice
        this.salePrice = updateDto.salePrice ?: this.salePrice
    }

    fun addRelativeProduct(relativeProduct: RelativeProduct) {
        RelativeProduct.validateProductIdMismatch(
            resultProductId = relativeProduct.resultProduct.id ?: 0,
            targetProductId = relativeProduct.targetProduct.id ?: 0
        )
        this.relativeProducts.add(relativeProduct)
    }

    fun removeRelativeProduct(resultProductId: Long) {
        val removeRelativeProduct = findRelativeProduct(resultProductId).get()
        this.relativeProducts.remove(removeRelativeProduct)
    }

    fun updateRelativeProduct(updateDto: RelativeProductUpdateDto, findProduct: (productId: Long) -> Product) {
        val relativeProduct = findRelativeProduct(updateDto.resultProductId).get()
        val isSameResultProduct = updateDto.resultProductId == updateDto.changeResultProductId

        if (isSameResultProduct.not()
            && findRelativeProduct(updateDto.changeResultProductId) != null
        ) {

            throw InValidException(InValidException.ALREADY_EXIST_UPDATE_RELATIVE_PRODUCT)
        }

        val changeResultProduct = if (isSameResultProduct) relativeProduct.resultProduct
        else findProduct(updateDto.changeResultProductId)

        val changeRelativeProduct = RelativeProduct(
            targetProduct = this,
            resultProduct = changeResultProduct,
            score = updateDto.score
        )

        this.relativeProducts.remove(relativeProduct)
        this.relativeProducts.add(changeRelativeProduct)
    }

    private fun findRelativeProduct(resultProductId: Long): RelativeProduct? =
        this.relativeProducts
            .find { relativeProduct ->
                val resultProduct = relativeProduct.resultProduct
                resultProduct.id == resultProductId
            }

    companion object {
        fun create(createDto: ProductCreateDto): Product =
            Product(
                name = createDto.name,
                image = createDto.image,
                url = createDto.url,
                originalPrice = createDto.originalPrice,
                salePrice = createDto.salePrice
            )
    }
}

fun Product?.get(): Product =
    this ?: throw NotFoundException(NotFoundException.PRODUCT)

fun Product.toDto(): ProductDto =
    ProductDto(
        productId = this.id!!,
        productName = this.name,
        productImage = this.image,
        productUrl = this.url,
        originalPrice = this.originalPrice,
        salePrice = this.salePrice
    )