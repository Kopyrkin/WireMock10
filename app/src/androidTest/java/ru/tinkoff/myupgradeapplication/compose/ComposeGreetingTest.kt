package ru.tinkoff.myupgradeapplication.compose

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.isNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class ComposeGreetingTest {

    /**
     * Указываем Rule
     */
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testButtonEnabled1() {
        /**
         * Выставляем composable-функцию, которую хотим тестировать.
         * Можно выставить как отдельную кнопку, так и целый экран
         */
        composeTestRule.setContent {
            Greeting()
        }

        /**
         * В compose элементы называются Node, чтобы взаимодействовать с ними нужно использовать composeTestRule
         * Функция onNode принимает SemanticsMatcher (например, hasText, hasTestTag) и находит нужный элемент
         * Далее можно производить над нодами проверки (assert) и действия (perform)
         */

        // 1. проверяем что кнопка неактивна
        composeTestRule.onNode(hasText("OK"))
            .assert(isNotEnabled())

        // 2. вводим текст
        composeTestRule.onNode(hasTestTag(GreetingTestTags.NAME_TEXT_INPUT))
            .performTextInput("Alex")

        // 3. проверям что кнопка активна
        composeTestRule.onNode(hasText("OK"))
            .assert(isEnabled())
    }

    @Test
    fun testButtonEnabled2() {
        composeTestRule.setContent {
            Greeting()
        }

        /**
         * Можно использовать удобные методы onNodeWithTag и onNodeWithText, assertIsEnabled() и т.п.
         */
        composeTestRule.onNodeWithText("OK").assertIsNotEnabled()
        composeTestRule.onNodeWithTag(GreetingTestTags.NAME_TEXT_INPUT).performTextInput("Alex")
        composeTestRule.onNodeWithText("OK").assertIsEnabled()
    }
}