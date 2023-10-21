package com.example.sample.domain.exception

class NotFoundException(message: String) : BusinessException(message) {
    companion object {
        const val DEFAULT = "정보를 찾을 수 없습니다."
        const val PRODUCT = "상품 정보를 찾을 수 없습니다."
        const val RELATIVE_PRODUCT = "연관 상품 정보를 찾을 수 없습니다."
    }
}