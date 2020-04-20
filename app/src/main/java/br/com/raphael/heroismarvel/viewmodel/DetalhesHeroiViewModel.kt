package br.com.raphael.heroismarvel.viewmodel

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.raphael.heroismarvel.App
import br.com.raphael.heroismarvel.R
import br.com.raphael.heroismarvel.components.LiveDataResult
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

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _success = MutableLiveData<LiveDataResult<Personagem>>()
    val success: MutableLiveData<LiveDataResult<Personagem>>
        get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    init {
        getApplication<App>().component.inject(this)
    }

    fun getHeroi(id: Int) {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                val response = backendRepository.getHeroiAsync(id)
                _success.postValue(LiveDataResult.success(response.data.results[0]))
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
                                    404 -> error?.status ?: resources.getString(R.string.msg_erro_http)
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
