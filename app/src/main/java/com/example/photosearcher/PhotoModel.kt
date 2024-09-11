package com.example.photosearcher

data class PhotoModel(
    val id: String,
    val description: String?,
    val urls: Urls
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)