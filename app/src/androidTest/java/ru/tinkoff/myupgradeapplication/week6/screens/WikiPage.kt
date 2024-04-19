package ru.tinkoff.myupgradeapplication.week6.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import ru.tinkoff.myupgradeapplication.R

class WikiPage {

    val buttonPreviousMatcher = withId(R.id.button_second)
    val buttonWikiSearch = withId(R.id.button_wiki_search)
    val editTextWikiFieldMatcher = withId(R.id.et_wiki_request)
    fun clickPreviousButton() {
        onView(buttonPreviousMatcher)
            .perform(click())
    }

    fun clickWikiSearchButton() {
        onView(buttonWikiSearch)
            .perform(click())
    }

    fun typeTextToWikiFiled(text: String) {
        onView(editTextWikiFieldMatcher)
            .perform(ViewActions.typeText(text))
        //.perform(ViewActions.replaceText(text))
        // Можно использовать replace в случае если набор тек ста с помощью typeText не срабатывает на некоторых view
    }

    fun replaceTextToWikiFiled(text: String) {
        onView(editTextWikiFieldMatcher)
            .perform(ViewActions.replaceText(text))
    }
}

