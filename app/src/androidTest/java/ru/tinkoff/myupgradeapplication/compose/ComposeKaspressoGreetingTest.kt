package ru.tinkoff.myupgradeapplication.compose

import androidx.compose.ui.test.junit4.createComposeRule
import com.kaspersky.components.composesupport.config.addComposeSupport
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.myupgradeapplication.compose.screens.KaspressoGreetingScreen
import ru.tinkoff.myupgradeapplication.week5.kaspressoBuilder

/**
 * В kaspressoBuilder нужно добавить поддержку compose: kaspressoBuilder.addComposeSupport()
 */
class ComposeKaspressoGreetingTest : TestCase(kaspressoBuilder.addComposeSupport()) {

    /**
     * Аналогично тестам без Kaspresso, здесь можно указать activity в createAndroidComposeRule
     * Тогде первым шагом теста вместо composeTestRule.setContent {}
     * нужно будет сделать переход на наш экран compose
     */
    @get:Rule
    val composeTestRule = createComposeRule()

    private val name = "Alex"

    @Test
    fun testGreetingText() = run {
        composeTestRule.setContent { Greeting() }

        onComposeScreen<KaspressoGreetingScreen>(composeTestRule) {
            step("Enter Name") {
                textInput.performTextInput(name)
            }
            step("Click OK Button") {
                okButton.performClick()
            }
            step("Check Greeting Text") {
                greetingText.assertExists()
                greetingText.assertTextEquals("Hello, $name!")
            }
        }
    }

    @Test
    fun testInputLabel() = run {
        composeTestRule.setContent { Greeting() }

        onComposeScreen<KaspressoGreetingScreen>(composeTestRule) {
            step("Input text") {
                textInput.performTextInput(name)
            }
            step("Assert input label") {
                /**
                 * Проверяем текст composable-функции лейбла
                 */
                textInputLabel.assertTextEquals("Enter your name")

                /**
                 * Merged semantics поля ввода содержит два текста: введенный текст name
                 * и текст лейбла
                 */
                textInput.assertTextEquals(name, "Enter your name")
            }
        }
    }

    @Test
    fun testInputError() = run {
        composeTestRule.setContent { Greeting() }

        onComposeScreen<KaspressoGreetingScreen>(composeTestRule) {
            step("Input invalid name") {
                textInput.performTextInput("123")
            }
            step("Click OK Button") {
                okButton.performClick()
            }
            step("Assert input error") {
                textInput.assert(hasError())
            }
        }
    }
}