package com.softsquared.template.kotlin.src.main.home.models

data class Result(
    val contents: List<Content>,
    val famousContents: List<FamousContent>,
    val products: List<Product>
)