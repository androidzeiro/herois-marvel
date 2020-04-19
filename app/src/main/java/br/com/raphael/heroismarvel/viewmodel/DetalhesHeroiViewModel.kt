package br.com.raphael.heroismarvel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.raphael.heroismarvel.App
import br.com.raphael.heroismarvel.model.Personagem
import br.com.raphael.heroismarvel.repository.BackendRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetalhesHeroiViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var backendRepository: BackendRepository

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String>
        get() = _erro

    private val _sucesso = MutableLiveData<Personagem>()
    val sucesso: LiveData<Personagem>
        get() = _sucesso

    init {
        getApplication<App>().component.inject(this)
    }

    fun getHeroi(id: Int) {
        viewModelScope.launch {
            try {
                val response = backendRepository.getHeroiAsync(id)
                _sucesso.postValue(response.data.results[0])
            } catch (e: Exception) { }
        }
    }
}
