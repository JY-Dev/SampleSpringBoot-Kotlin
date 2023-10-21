package com.example.sample.database.repository.product

import com.example.sample.domain.entity.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCommandRepository : JpaRepository<Product, Long>