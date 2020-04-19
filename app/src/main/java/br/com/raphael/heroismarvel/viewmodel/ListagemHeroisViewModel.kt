package br.com.raphael.heroismarvel.viewmodel

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.raphael.heroismarvel.App
import br.com.raphael.heroismarvel.R
import br.com.raphael.heroismarvel.extensions.getResponse
import br.com.raphael.heroismarvel.extensions.tryParse
import br.com.raphael.heroismarvel.model.Personagem
import br.com.raphael.heroismarvel.model.ResponseBody
import br.com.raphael.heroismarvel.repository.BackendRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class ListagemHeroisViewModel(application: Application) : AndroidViewModel(application) {

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String>
        get() = _erro

    private val _todos = MutableLiveData<List<Personagem>>()
    val todos: LiveData<List<Personagem>>
        get() = _todos

    private val _avengers = MutableLiveData<List<Personagem>>()
    val avengers: LiveData<List<Personagem>>
        get() = _avengers
    
    @Inject
    lateinit var backendRepository: BackendRepository
    @Inject
    lateinit var resources: Resources

    init {
        getApplication<App>().component.inject(this)
        getHerois()
    }

    fun getHerois() {
        viewModelScope.launch {
            try {
                val response = backendRepository.getHeroisAsync()
                _todos.postValue(response.data.results)
                println("11111")
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        val erroJson = e.getResponse() ?: ""
                        val error =
                            Gson().tryParse<ResponseBody>(
                                erroJson
                            )

                        _erro.postValue(
                            resources.getString(R.string.msg_erro_api).format(
                                when (e.code()) {
                                    409 -> error?.status ?: resources.getString(R.string.msg_erro_http)
                                    else -> resources.getString(R.string.msg_erro_http)
                                },
                                e.code()
                            )
                        )
                    }
                    else -> _erro.postValue(e.toString())
                }
            }
        }
    }

    fun getAvengers() {
        viewModelScope.launch {
            try {
                val response = backendRepository.getAvengers()
                _avengers.postValue(response)
                println("11111")
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        val erroJson = e.getResponse() ?: ""
                        val error =
                            Gson().tryParse<ResponseBody>(
                                erroJson
                            )

                        _erro.postValue(
                            resources.getString(R.string.msg_erro_api).format(
                                when (e.code()) {
                                    409 -> error?.status ?: resources.getString(R.string.msg_erro_http)
                                    else -> resources.getString(R.string.msg_erro_http)
                                },
                                e.code()
                            )
                        )
                    }
                    else -> _erro.postValue(e.toString())
                }
            }
        }
    }
}
