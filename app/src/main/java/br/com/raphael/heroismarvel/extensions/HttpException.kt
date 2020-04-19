package br.com.raphael.heroismarvel.extensions

import br.com.raphael.heroismarvel.model.ResponseBody
import com.google.gson.Gson
import retrofit2.HttpException

fun HttpException.getResponse() = response()?.errorBody()?.string()