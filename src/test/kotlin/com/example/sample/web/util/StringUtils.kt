package com.example.sample.web.util

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

class StringUtils {

    @Test
    fun numericStringSplitterTest(){
        val data = "1,2,3"
        val test = data.numericStringSplitter(Delimiter.COMMA)
        assertIterableEquals(listOf(1L,2L,3L),test)
    }

    @Test
    fun numericStringSplitterInValidTest(){
        val data = "1,2,"
        val test = data.numericStringSplitter(Delimiter.COMMA)
        assertIterableEquals(listOf(1L,2L),test)
    }
}