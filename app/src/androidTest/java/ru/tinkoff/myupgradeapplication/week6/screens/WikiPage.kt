package ru.tinkoff.myupgradeapplication.week6.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import ru.tinkoff.myupgradeapplication.R

class WikiPage {

    val buttonPreviousMatcher = withId(R.id.button_second)
    val buttonWikiSearch = withId(R.id.button_wiki_search)
    val editTextWikiFieldMatcher = withId(R.id.et_wiki_request)
    val textToWikiSearchCaseImperatrica = "В объятьях юных кавалеров забывает обо всем"
    val textToWikiSearchCaseSeregaFirstAnswer = "Как правильно точить ножи"
    val textToWikiSearchCaseSeregaNextAnswer = "Как при походке ставить ноги"
    val textToWikiSearchCaseSlon = ""
    val textOnWikiResult = withId(R.id.tw_wiki_result)
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
    fun checkWikiResult(text: String) {
        onView(textOnWikiResult)
           .check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }
}

