package br.com.raphael.heroismarvel.model

data class Response(
    val code: Int,
    val etag: String,
    val data: Data
)