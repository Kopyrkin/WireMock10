package ru.tinkoff.myupgradeapplication.week6.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import ru.tinkoff.myupgradeapplication.R
import ru.tinkoff.myupgradeapplication.week6.elements.PersonView

class StartPage {
    val firstButtonMatcher = withId(R.id.button_first)
    val buttonShowPerson = withId(R.id.show_person)

    val fabMatcher = withId(R.id.fab)

    val personView = PersonView()

    fun clickFirstButton() {
        onView(firstButtonMatcher)
            .perform(click())
    }

    fun clickShowPersonButton() {
        onView(buttonShowPerson)
            .perform(click())
    }

    fun clickFab() {
        onView(fabMatcher)
            .perform(click())
            .check(matches(isDisplayed()))
    }

}

