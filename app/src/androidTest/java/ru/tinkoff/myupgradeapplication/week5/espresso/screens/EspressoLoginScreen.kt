package ru.tinkoff.myupgradeapplication.week5.espresso.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.android.material.snackbar.SnackbarContentLayout
import com.google.android.material.textview.MaterialTextView
import org.hamcrest.Matchers
import ru.tinkoff.myupgradeapplication.R
import ru.tinkoff.myupgradeapplication.week5.matchers.TextViewHintColorMatcher

class EspressoLoginScreen {

    val buttonPreviousMatcher  = withId(R.id.button_second)
    val buttonSubmitMatcher  = withId(R.id.button_submit)
    val editTextLoginFieldMatcher = withId(R.id.edittext_login)
    val editTextPasswordFieldMatcher = withId(R.id.edittext_password)

    val snackBarTextViewMatcher = Matchers.allOf(
        ViewMatchers.withParent(Matchers.instanceOf(SnackbarContentLayout::class.java)),
        Matchers.instanceOf(MaterialTextView::class.java)
    )

    fun clickPreviousButton(){
        onView(buttonPreviousMatcher)
            .perform(click())
    }

    fun clickSubmitButton(){
        onView(buttonSubmitMatcher)
            .perform(click())
    }

    fun typeTextToLoginFiled(text: String) {
        onView(editTextLoginFieldMatcher)
            .perform(typeText(text))
    }
    fun typeTextToPasswordFiled(text: String) {
        onView(editTextPasswordFieldMatcher)
            .perform(typeText(text))
    }

    fun checkTextOnSnackBar(text: String) {
        onView(snackBarTextViewMatcher)
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }

    fun checkLoginHintColor(errorHintId: Int) {
        onView(editTextLoginFieldMatcher)
            .check(ViewAssertions.matches(TextViewHintColorMatcher(errorHintId)))
    }
}