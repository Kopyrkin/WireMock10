package ru.tinkoff.myupgradeapplication.compose

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import ru.tinkoff.myupgradeapplication.R
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.myupgradeapplication.MainActivity
import ru.tinkoff.myupgradeapplication.compose.screens.GreetingScreen
import ru.tinkoff.myupgradeapplication.week5.espresso.screens.EspressoFirstScreen

class ComposeActivityGreetingTest {

    /**
     * В AndroidComposeRule нужно передать Activity, которую хотим открыть
     */
    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun testButtonEnabled() {
        // Сначала переходим на Greeting экран
        EspressoFirstScreen().clickComposeButton()

        // Теперь у нас есть доступ к activity, можно получить строковый ресурс
        val okButtonName = composeTestRule.activity.getString(R.string.ok_button)

        // Делаем проверки на compose экране
        composeTestRule.onNodeWithText(okButtonName).assertIsNotEnabled()
        composeTestRule.onNodeWithTag(GreetingTestTags.NAME_TEXT_INPUT).performTextInput("Alex")
        composeTestRule.onNodeWithText(okButtonName).assertIsEnabled()
    }

    @Test
    fun testButtonEnabledWithScreen() {
        EspressoFirstScreen().clickComposeButton()

        with(GreetingScreen(composeTestRule)) {
            okButton.assertIsNotEnabled()
            textInput.performTextInput("Alex")
            okButton.assertIsEnabled()
        }
    }
}