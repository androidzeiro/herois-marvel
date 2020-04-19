package br.com.raphael.heroismarvel.model

data class Data(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Personagem>
)