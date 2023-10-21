package com.example.sample.web.api

import com.example.sample.database.dto.ProductSearchCondition
import com.example.sample.database.service.product.RelativeProductCRUDService
import com.example.sample.domain.dto.product.CombinedProductAndRelativeDto
import com.example.sample.web.dto.product.request.RelativeProductRegisterRequest
import com.example.sample.web.dto.product.request.RelativeProductRemoveRequest
import com.example.sample.web.dto.product.request.RelativeProductUpdateRequest
import com.example.sample.web.exception.RequestParamException
import com.example.sample.web.util.Delimiter
import com.example.sample.web.util.numericStringSplitter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/relative-product")
@RestController
class RelativeProductCRUDController(
    private val relativeProductCRUDService: RelativeProductCRUDService
) {

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    fun addRelativeProduct(
        @RequestBody registerRequest: RelativeProductRegisterRequest
    ) {
        relativeProductCRUDService.addRelativeProduct(
            dto = registerRequest.toDomain()
        )
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping
    fun removeRelativeProduct(
        @RequestBody removeRequest: RelativeProductRemoveRequest
    ) {
        relativeProductCRUDService.removeRelativeProduct(
            dto = removeRequest.toDomain()
        )
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PutMapping
    fun updateProduct(
        @RequestBody updateRequest: RelativeProductUpdateRequest,
    ) {
        relativeProductCRUDService.updateRelativeProduct(
            dto = updateRequest.toDomain()
        )
    }

    @GetMapping
    fun fetchCombinedProductAndRelative(
        @RequestParam(required = false) productIds: String?
    ): CombinedProductAndRelativeDto {
        return try {
            val condition = ProductSearchCondition(
                productIds = productIds?.numericStringSplitter(Delimiter.COMMA) ?: emptyList()
            )
            relativeProductCRUDService.getCombinedProductAndRelative(condition)
        } catch (e: NumberFormatException) {
            throw RequestParamException()
        }
    }
}