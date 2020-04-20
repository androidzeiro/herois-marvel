package br.com.raphael.heroismarvel.helpers

import android.view.View
import android.widget.EditText
import androidx.core.util.Preconditions.checkArgument

import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.core.Is.`is`


object Matchers {
    fun withItemHint(hintText: String?): BoundedMatcher<View?, EditText> {
        checkArgument(hintText != null)
        return withItemHint(`is`(hintText))
    }

    private fun withItemHint(matcherText: Matcher<String?>): BoundedMatcher<View?, EditText> {
        return object : BoundedMatcher<View?, EditText>(EditText::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with item hint: $matcherText")
            }

            override fun matchesSafely(editTextField: EditText): Boolean {
                return matcherText.matches(editTextField.hint.toString())
            }
        }
    }
}