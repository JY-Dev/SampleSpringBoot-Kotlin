package com.example.sample.domain.exception

class InValidException(message: String) : BusinessException(message) {

    companion object {
        const val DEFAULT = "유효하지 않습니다."
        const val ALREADY_EXIST_UPDATE_RELATIVE_PRODUCT = "변경하고자 하는 연관 상품이 이미 존재합니다."
    }
}