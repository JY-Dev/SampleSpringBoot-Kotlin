package com.example.sample.web.api

import com.example.sample.database.service.product.ProductCRUDService
import com.example.sample.web.dto.product.request.ProductRegisterRequest
import com.example.sample.web.dto.product.request.ProductUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/product")
@RestController
class ProductCRUDController(
    private val productCRUDService: ProductCRUDService
) {

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    fun registerProduct(
        @RequestBody registerRequest: ProductRegisterRequest
    ) {
        productCRUDService.registerProduct(
            dto = registerRequest.toDomain()
        )
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{productId}")
    fun deleteProduct(
        @PathVariable productId: Long
    ) {
        productCRUDService.deleteProduct(
            productId = productId
        )
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PatchMapping("/{productId}")
    fun updateProduct(
        @RequestBody updateRequest: ProductUpdateRequest,
        @PathVariable productId: Long
    ) {
        productCRUDService.updateProduct(
            dto = updateRequest.toDomain(),
            productId = productId
        )
    }

    @GetMapping("/{productId}")
    fun fetchProduct(
        @PathVariable productId: Long
    ) = productCRUDService.getProduct(productId)

    @GetMapping
    fun fetchProducts() =
        productCRUDService.getProducts()

}