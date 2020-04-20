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

class DetalhesHeroiViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var backendRepository: BackendRepository
    @Inject
    lateinit var resources: Resources

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String>
        get() = _erro

    private val _sucesso = MutableLiveData<Personagem>()
    val sucesso: LiveData<Personagem>
        get() = _sucesso

    private val _carregando = MutableLiveData<Boolean>()
    val carregando: LiveData<Boolean>
        get() = _carregando

    init {
        getApplication<App>().component.inject(this)
    }

    fun getHeroi(id: Int) {
        viewModelScope.launch {
            try {
                _carregando.postValue(true)
                val response = backendRepository.getHeroiAsync(id)
                _sucesso.postValue(response.data.results[0])
                _carregando.postValue(false)
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
                                    404 -> error?.status ?: resources.getString(R.string.msg_erro_http)
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
