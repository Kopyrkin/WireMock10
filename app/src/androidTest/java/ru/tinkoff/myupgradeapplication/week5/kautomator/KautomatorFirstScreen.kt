package ru.tinkoff.myupgradeapplication.week5.kautomator

import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.screen.UiScreen

class KautomatorFirstScreen: UiScreen<KautomatorFirstScreen>(){
    override val packageName: String = PACKAGE

    val nextButton = UiButton { withId(this@KautomatorFirstScreen.packageName, "button_first") }

    companion object {
        inline operator fun invoke(crossinline block: KautomatorFirstScreen.() -> Unit) =
            KautomatorFirstScreen().block()
    }
}
