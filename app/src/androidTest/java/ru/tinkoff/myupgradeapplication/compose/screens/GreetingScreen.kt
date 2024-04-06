package ru.tinkoff.myupgradeapplication.compose.screens

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import ru.tinkoff.myupgradeapplication.compose.GreetingTestTags

/**
 * ComposeTestRule является наследником SemanticsNodeInteractionsProvider,
 * в котором содержатся все нужные нам методы для поиска node
 */
class GreetingScreen(private val semanticsProvider: SemanticsNodeInteractionsProvider) {

    val okButton: SemanticsNodeInteraction
        get() = semanticsProvider.onNodeWithText("OK")

    val textInput: SemanticsNodeInteraction
        get() = semanticsProvider.onNodeWithTag(GreetingTestTags.NAME_TEXT_INPUT)
}