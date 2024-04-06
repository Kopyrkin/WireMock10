package ru.tinkoff.myupgradeapplication.week5.espresso.screens

import androidx.appcompat.widget.AppCompatTextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.google.android.material.textview.MaterialTextView
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import ru.tinkoff.myupgradeapplication.R

class EspressoFirstScreen {
    val firstButtonMatcher = withId(R.id.button_first)
    val composeButtonMatcher = withId(R.id.compose_button)
    val snackBarTextViewMatcher = allOf(
        ViewMatchers.withParent(Matchers.instanceOf(SnackbarContentLayout::class.java)),
        Matchers.instanceOf(MaterialTextView::class.java)
    )

    val fabMatcher = withId(R.id.fab)
    val screenTitleMatcher = allOf(
        Matchers.instanceOf(AppCompatTextView::class.java),
        ViewMatchers.withParent(Matchers.instanceOf(MaterialToolbar::class.java))
    )

    fun clickFirstButton(){
        onView(firstButtonMatcher)
            .perform(click())
    }

    fun clickComposeButton(){
        onView(composeButtonMatcher)
            .perform(click())
    }

    fun checkTextOnSnackBar(text: String) {
        onView(snackBarTextViewMatcher)
            .check(matches(withText(text)))
    }

    fun clickFab(){
        onView(fabMatcher)
            .perform(click())
    }

    fun checkScreenTitile(title : String) {
        onView(screenTitleMatcher)
            .check(matches(withText(title)))
    }
}