package br.com.raphael.heroismarvel.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario.launchInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import br.com.raphael.heroismarvel.MainActivity
import br.com.raphael.heroismarvel.R
import br.com.raphael.heroismarvel.TestApp
import br.com.raphael.heroismarvel.di.TestComponent
import br.com.raphael.heroismarvel.repository.BackendRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class ListagemHeroisFragmentTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mRule = ActivityTestRule(MainActivity::class.java)

    @Inject
    lateinit var backendRepository: BackendRepository

    @Before
    fun init() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as TestApp
        val component = app.component as TestComponent

        component.inject(this)
    }

    // Visível
    @Test
    fun recyclerViewVisivel() {
        launchInContainer(
            ListagemHeroisFragment::class.java,
            null,
            R.style.AppTheme,
            null
        )

        onView(withId(R.id.rv_listagem))
            .check(matches(isDisplayed()))
    }

    @Test
    fun floatButtonAllCharactersVisivel() {
        launchInContainer(
            ListagemHeroisFragment::class.java,
            null,
            R.style.AppTheme,
            null
        )

        onView(withId(R.id.fb_all))
            .check(matches(isDisplayed()))
    }

    @Test
    fun floatButtonAvengersVisivel() {
        launchInContainer(
            ListagemHeroisFragment::class.java,
            null,
            R.style.AppTheme,
            null
        )

        onView(withId(R.id.fb_avengers))
            .check(matches(isDisplayed()))
    }

    // Clicavel
    @Test
    fun floatButtonAllCharactersClicavel() {
        launchInContainer(
            ListagemHeroisFragment::class.java,
            null,
            R.style.AppTheme,
            null
        )

        onView(withId(R.id.fb_all)).check(matches(isClickable()))
    }

    @Test
    fun floatButtonAvengersClicavel() {
        launchInContainer(
            ListagemHeroisFragment::class.java,
            null,
            R.style.AppTheme,
            null
        )

        onView(withId(R.id.fb_avengers)).check(matches(isClickable()))
    }
}