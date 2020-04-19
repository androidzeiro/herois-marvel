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

class ListagemHeroisViewModel(application: Application) : AndroidViewModel(application) {

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String>
        get() = _erro

    private val _herois = MutableLiveData<List<Personagem>>()
    val herois: LiveData<List<Personagem>>
        get() = _herois
    
    @Inject
    lateinit var backendRepository: BackendRepository

    init {
        getApplication<App>().component.inject(this)
        getHerois()
    }

    fun getHerois() {
        viewModelScope.launch {
            try {
                val response = backendRepository.getHeroisAsync()
                _herois.postValue(response.data.results)
            } catch (e: Exception) { }
        }
    }
}
