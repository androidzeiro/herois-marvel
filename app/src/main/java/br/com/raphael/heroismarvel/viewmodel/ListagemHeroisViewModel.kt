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

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _allCharacters = MutableLiveData<List<Personagem>>()
    val allCharacters: LiveData<List<Personagem>>
        get() = _allCharacters

    private val _avengers = MutableLiveData<List<Personagem>>()
    val avengers: LiveData<List<Personagem>>
        get() = _avengers

    private val _turn = MutableLiveData<Boolean>()
    val turn: LiveData<Boolean>
        get() = _turn

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    @Inject
    lateinit var backendRepository: BackendRepository
    @Inject
    lateinit var resources: Resources

    init {
        getApplication<App>().component.inject(this)
        getHerois()
    }

    fun getHerois() {
        _turn.postValue(true)
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                val response = backendRepository.getHeroisAsync()
                _allCharacters.postValue(response.data.results)
                _loading.postValue(false)
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        val erroJson = e.getResponse() ?: ""
                        val error =
                            Gson().tryParse<ResponseBody>(
                                erroJson
                            )

                        _error.postValue(
                            resources.getString(R.string.msg_erro_api).format(
                                when (e.code()) {
                                    409 -> error?.status ?: resources.getString(R.string.msg_erro_http)
                                    else -> resources.getString(R.string.msg_erro_http)
                                },
                                e.code()
                            )
                        )
                    }
                    else -> _error.postValue(e.toString())
                }
            }
        }
    }

    fun getAvengers() {
        _turn.postValue(false)
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                val response = backendRepository.getAvengers()
                _avengers.postValue(response)
                _loading.postValue(false)
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        val erroJson = e.getResponse() ?: ""
                        val error =
                            Gson().tryParse<ResponseBody>(
                                erroJson
                            )

                        _error.postValue(
                            resources.getString(R.string.msg_erro_api).format(
                                when (e.code()) {
                                    409 -> error?.status ?: resources.getString(R.string.msg_erro_http)
                                    else -> resources.getString(R.string.msg_erro_http)
                                },
                                e.code()
                            )
                        )
                    }
                    else -> _error.postValue(e.toString())
                }
            }
        }
    }
}
