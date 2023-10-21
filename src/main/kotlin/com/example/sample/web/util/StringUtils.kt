package com.example.sample.web.util

fun String.numericStringSplitter(delimiter: Delimiter): List<Long> {
    return this.split(delimiter.value)
        .map {
            it.toLong()
        }
}