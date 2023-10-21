package com.example.sample.domain

import com.example.sample.domain.dto.product.RelativeProductUpdateDto
import com.example.sample.domain.entity.product.Product
import com.example.sample.domain.entity.product.RelativeProduct
import com.example.sample.domain.exception.InValidException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ProductTest {

    @DisplayName("연관 상품 코드와 입력 상품 코드가 일치 하는 경우 익셉션 발생")
    @Test
    fun add_relative_product_invalid_test() {
        val product = createMockProduct(0L)
        val product1 = createMockProduct(1L)
        val product2 = createMockProduct(1L)
        val relativeProduct = RelativeProduct(product1,product2,0,0)
        Assertions.assertThrows(InValidException::class.java){
            product.addRelativeProduct(relativeProduct)
        }
    }

    @DisplayName("연관 상품 코드와 입력 상품 코드가 일치 하지 않는 경우 정상 처리")
    @Test
    fun add_relative_product_valid_test() {
        val product = createMockProduct(0L)
        val product1 = createMockProduct(1L)
        val product2 = createMockProduct(2L)
        val relativeProduct = RelativeProduct(product1,product2,0,0)
        product.addRelativeProduct(relativeProduct)
        org.assertj.core.api.Assertions.assertThat(product.relativeProducts.size).isEqualTo(1)
    }

    @DisplayName("상품에 이미 동일한 연관상품이 존재하는 경우 중복되지 않도록 처리")
    @Test
    fun add_relative_product_duplicate_test() {
        val product = createMockProduct(0L)
        val product1 = createMockProduct(1L)
        val product2 = createMockProduct(2L)
        val relativeProduct1 = RelativeProduct(product1,product2,0)
        val relativeProduct2 = RelativeProduct(product1,product2,0)
        product.addRelativeProduct(relativeProduct1)
        product.addRelativeProduct(relativeProduct2)
        org.assertj.core.api.Assertions.assertThat(product.relativeProducts.size).isEqualTo(1)
    }

    @DisplayName("상품에 이미 동일한 연관상품이 존재하지 않는 경우 정상 처리")
    @Test
    fun add_relative_product_not_duplicate_test() {
        val product = Product("test","test","test",0,0, id = 0L)
        val product1 = createMockProduct(1L)
        val product2 = createMockProduct(2L)
        val product3 = createMockProduct(3L)
        val relativeProduct1 = RelativeProduct(product1,product2,0)
        val relativeProduct2 = RelativeProduct(product1,product3,0)
        product.addRelativeProduct(relativeProduct1)
        product.addRelativeProduct(relativeProduct2)
        org.assertj.core.api.Assertions.assertThat(product.relativeProducts.size).isEqualTo(2)
    }

    @DisplayName("연관상품 삭제")
    @Test
    fun remove_relative_product_test() {
        val product = createMockProduct(0L)
        val product2 = createMockProduct(1L)
        val relativeProduct1 = RelativeProduct(product,product2,0)
        product.addRelativeProduct(relativeProduct1)
        product.removeRelativeProduct(product2.id!!)
        org.assertj.core.api.Assertions.assertThat(product.relativeProducts.size).isEqualTo(0)
    }

    @DisplayName("연관상품 삭제 엣지 케이스")
    @Test
    fun remove_relative_product_edge_test() {
        val product = createMockProduct(0L)
        val product2 = createMockProduct(1L)
        val product3 = createMockProduct(2L)
        val relativeProduct1 = RelativeProduct(product,product2,0)
        val relativeProduct2 = RelativeProduct(product,product3,0)
        product.addRelativeProduct(relativeProduct1)
        product.addRelativeProduct(relativeProduct2)
        product.removeRelativeProduct(product2.id!!)
        org.assertj.core.api.Assertions.assertThat(product.relativeProducts.contains(relativeProduct2)).isTrue()
    }

    @DisplayName("연관상품 업데이트")
    @Test
    fun update_relative_product_test() {
        val product = createMockProduct(0L)
        val product2 = createMockProduct(1L)
        val product3 = createMockProduct(2L)
        val relativeProduct = RelativeProduct(product,product2,0)
        product.addRelativeProduct(relativeProduct)
        val relativeProductUpdateDto = RelativeProductUpdateDto(product.id!!,product2.id!!,product3.id!!,20)
        product.updateRelativeProduct(relativeProductUpdateDto){
            createMockProduct(it)
        }
        val changeRelativeProduct = RelativeProduct(product,product3,20)
        val updateScore = product.relativeProducts.find { it.resultProduct == product3 }?.score
        org.assertj.core.api.Assertions.assertThat(updateScore).isEqualTo(relativeProductUpdateDto.score)
        org.assertj.core.api.Assertions.assertThat(product.relativeProducts.contains(changeRelativeProduct)).isTrue()
    }

    @DisplayName("연관상품 업데이트시에 바꾸고자 하는 연관상품이 이미 존재하는 경우 예외 발생")
    @Test
    fun update_relative_product_already_exist_test() {
        val product = createMockProduct(0L)
        val product2 = createMockProduct(1L)
        val product3 = createMockProduct(2L)
        val relativeProduct = RelativeProduct(product,product2,0)
        val relativeProduct2 = RelativeProduct(product,product3,0)

        product.addRelativeProduct(relativeProduct)
        product.addRelativeProduct(relativeProduct2)

        val relativeProductUpdateDto = RelativeProductUpdateDto(product.id!!,product2.id!!,product3.id!!,20)
        Assertions.assertThrows(InValidException::class.java){
            product.updateRelativeProduct(relativeProductUpdateDto){
                createMockProduct(it)
            }
        }
    }

    @DisplayName("연관상품 업데이트시에 score만 변경하는 테스트")
    @Test
    fun update_relative_product_score_test() {
        val product = createMockProduct(0L)
        val product2 = createMockProduct(1L)
        val relativeProduct = RelativeProduct(product,product2,0)

        product.addRelativeProduct(relativeProduct)

        val relativeProductUpdateDto = RelativeProductUpdateDto(product.id!!,product2.id!!,product2.id!!,20)
        product.updateRelativeProduct(relativeProductUpdateDto){
            createMockProduct(it)
        }
        val resultProduct = product.relativeProducts.find { it.resultProduct == product2 }
        org.assertj.core.api.Assertions.assertThat(resultProduct).isNotNull
        org.assertj.core.api.Assertions.assertThat(resultProduct?.score).isEqualTo(relativeProductUpdateDto.score)
    }

    private fun createMockProduct(id : Long) =
        Product("test","test","test",0,0, id = id)
}