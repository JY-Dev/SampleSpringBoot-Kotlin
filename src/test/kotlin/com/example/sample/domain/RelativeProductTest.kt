package com.example.sample.domain

import com.example.sample.domain.entity.product.RelativeProduct
import com.example.sample.domain.exception.InValidException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class RelativeProductTest {

    @DisplayName("연관 상품 코드와 입력 상품 코드가 일치 하는 경우 익셉션 발생")
    @Test
    fun validate_product_id_mismatch_invalid_test(){
        Assertions.assertThrows(InValidException::class.java){
            RelativeProduct.validateProductIdMismatch(0,0)
        }
    }

    @DisplayName("연관 상품 코드와 입력 상품 코드가 일치 하지 않는 경우 정상 처리")
    @Test
    fun validate_product_id_mismatch_valid_test(){
        RelativeProduct.validateProductIdMismatch(1,0)
    }
}