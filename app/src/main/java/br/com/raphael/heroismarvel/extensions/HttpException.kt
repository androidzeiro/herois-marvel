package br.com.raphael.heroismarvel.extensions

import retrofit2.HttpException

fun HttpException.getResponse() = response()?.errorBody()?.string()