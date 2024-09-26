package ru.tinkoff.myupgradeapplication.week6.elements

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.containsString
import ru.tinkoff.myupgradeapplication.R

class PersonView {
    fun checkFio(fio: String) {
        onView(fioMatcher).check(matches(withText(fio)))
    }
    fun checkFioContains(fio: String) {
        onView(fioMatcher).check(matches(withText(containsString(fio))))
    }
    fun checkImageDisplayed() {
        onView(imageMatcher).check(matches(isCompletelyDisplayed()))
    }
    fun checkEmail(email: String) {
        onView(emailMatcher).check(matches(withText(email)))
    }
    fun checkEmailDisplayed() {
        onView(emailMatcher).check(matches(isCompletelyDisplayed()))
    }

    private val emailMatcher = withId(R.id.person_email)
    private val fioMatcher = withId(R.id.person_name)
    private val imageMatcher = withId(R.id.person_avatar)
}

