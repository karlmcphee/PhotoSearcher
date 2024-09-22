package com.example.photosearcher

data class PhotoModel(
    //val id: String,
    //val description: String?,
    var total: Int,
    var results: ArrayList<Results>
)

data class Results(
    var id: String,
    var urls: Urls
)
data class Urls(
    var raw: String,
    var full: String,
    var regular: String,
    var small: String,
    var thumb: String
)