package br.com.raphael.heroismarvel.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphael.heroismarvel.App
import br.com.raphael.heroismarvel.components.LiveDataResult
import br.com.raphael.heroismarvel.repository.BackendRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @MockK
    lateinit var app: App
    lateinit var backendRepository: BackendRepository
    lateinit var detalhesHeroiViewModel: DetalhesHeroiViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        detalhesHeroiViewModel = DetalhesHeroiViewModel(app)
    }

    @Test
    fun testSuccess() {
        coEvery { backendRepository.getHeroiAsync(any()) }
        detalhesHeroiViewModel.sucesso.observeForever {}
        detalhesHeroiViewModel.getHeroi(0)

        assert(detalhesHeroiViewModel.sucesso.value != null)
        assert(detalhesHeroiViewModel.sucesso.value!!.status == LiveDataResult.STATUS.SUCCESS)
    }

    @Test
    fun testError() {
        coEvery { backendRepository.getHeroiAsync(any()) } coAnswers { throw Exception("No network") }
        detalhesHeroiViewModel.sucesso.observeForever {}
        detalhesHeroiViewModel.getHeroi(0)

        assert(detalhesHeroiViewModel.sucesso.value != null)
        assert(detalhesHeroiViewModel.sucesso.value!!.status == LiveDataResult.STATUS.ERROR)
    }
}