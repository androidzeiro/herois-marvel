package br.com.raphael.heroismarvel.model

data class Personagem(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)