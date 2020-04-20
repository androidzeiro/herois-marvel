package br.com.raphael.heroismarvel.ui

import android.os.Bundle
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario.launchInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import br.com.raphael.heroismarvel.MainActivity
import br.com.raphael.heroismarvel.R
import br.com.raphael.heroismarvel.TestApp
import br.com.raphael.heroismarvel.di.TestComponent
import br.com.raphael.heroismarvel.repository.BackendRepository
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class DetalhesHeroiFragmentTest {
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
    fun imagemViewVisivel() {
        launchInContainer(
            DetalhesHeroiFragment::class.java,
            Bundle().apply { putInt("id", 0) },
            R.style.AppTheme,
            null
        )

        onView(withId(R.id.iv_heroi_detalhes))
            .check(matches(isDisplayed()))
    }

    @Test
    fun nomeVisivel() {
        launchInContainer(
            DetalhesHeroiFragment::class.java,
            Bundle().apply { putInt("id", 0) },
            R.style.AppTheme,
            null
        )

        onView(withId(R.id.tv_nome))
            .check(matches(isDisplayed()))
    }


    @Test
    fun descricaoVisivel() {
        launchInContainer(
            DetalhesHeroiFragment::class.java,
            Bundle().apply { putInt("id", 0) },
            R.style.AppTheme,
            null
        )

        onView(withId(R.id.tv_descricao))
            .check(matches(isDisplayed()))
    }
}