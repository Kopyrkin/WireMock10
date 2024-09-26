package ru.tinkoff.myupgradeapplication.week5.kautomator

import com.kaspersky.components.kautomator.component.edit.UiEditText
import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.component.text.UiTextView
import com.kaspersky.components.kautomator.screen.UiScreen

class KautomatorLoginScreen : UiScreen<KautomatorLoginScreen>() {
    override val packageName: String = PACKAGE

    val login = UiEditText { withId(this@KautomatorLoginScreen.packageName, "edittext_login") }
    val password = UiEditText { withId(this@KautomatorLoginScreen.packageName, "edittext_password")}
    val submit = UiButton { withId(this@KautomatorLoginScreen.packageName, "button_submit") }
    val snack = UiTextView  { withId(this@KautomatorLoginScreen.packageName, "snackbar_text") }

    companion object {
        inline operator fun invoke(crossinline block: KautomatorLoginScreen.() -> Unit) =
            KautomatorLoginScreen().block()
    }

}