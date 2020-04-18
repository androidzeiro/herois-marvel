package br.com.raphael.heroismarvel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.raphael.heroismarvel.App
import br.com.raphael.heroismarvel.repository.BackendRepository
import javax.inject.Inject

class DetalhesHeroiViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var backendRepository: BackendRepository

    init {
        getApplication<App>().component.inject(this)
    }
}
