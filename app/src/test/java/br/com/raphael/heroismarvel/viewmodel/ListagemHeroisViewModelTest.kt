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
class ListagemHeroisViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @MockK
    lateinit var app: App
    lateinit var backendRepository: BackendRepository
    lateinit var listagemHeroisViewModel: ListagemHeroisViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        listagemHeroisViewModel = ListagemHeroisViewModel((app))
    }

    @Test
    fun testSuccessAllCharacters() {
        coEvery { backendRepository.getHeroiAsync(any()) }
        listagemHeroisViewModel.allCharacters.observeForever {}
        listagemHeroisViewModel.getHerois()

        assert(listagemHeroisViewModel.allCharacters.value != null)
        assert(listagemHeroisViewModel.allCharacters.value!!.status == LiveDataResult.STATUS.SUCCESS)
    }

    @Test
    fun testErrorAllCharacters() {
        coEvery { backendRepository.getHeroiAsync(any()) } coAnswers { throw Exception("No network") }
        listagemHeroisViewModel.allCharacters.observeForever {}
        listagemHeroisViewModel.getHerois()

        assert(listagemHeroisViewModel.allCharacters.value != null)
        assert(listagemHeroisViewModel.allCharacters.value!!.status == LiveDataResult.STATUS.ERROR)
    }
}