package ru.tinkoff.myupgradeapplication.week5.kaspresso

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.myupgradeapplication.MainActivity
import ru.tinkoff.myupgradeapplication.week5.kaspressoBuilder

class KaspressoTest :TestCase(kaspressoBuilder) {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun kaspressoTest() =
        before {
            testLogger.i("action before test in test context")
        }.after {
            testLogger.i("action after test in test context")
        }.run {
            val loginValue = "Tinkoff"
            val passwordValue = "Upgrade"
            KaspressoFirstScreen {
                nextButton.click()
            }
            KaspressoLoginScreen {
                editTextlogin.typeText(loginValue)
                editTextpassword.typeText(passwordValue)
                buttonSubmit.click()
                textOnSnackBar.hasText("You enter login = $loginValue password = $passwordValue")
            }

        }

    @Test
    fun kaspressoTestFlaky() =
        run {
            val loginValue = "Tinkoff"
            val passwordValue = "Upgrade"
            step("Navigate to Login Screen") {
                KaspressoFirstScreen {
                    nextButton.click()
                }
            }
            step("Check notification") {
                KaspressoLoginScreen {
                    step("Enter login, password") {
                        editTextlogin.typeText(loginValue)
                        editTextpassword {
                            typeText(passwordValue)
                            flakySafely (20_000) {
                                hasText(loginValue)
                            }
                        }
                    }

                    step("Click Confirm") {
                        buttonSubmit.click()
                    }
                    step("Check notification text") {
                        textOnSnackBar.hasText("You enter login = $loginValue password = $passwordValue")
                    }
                }
            }
        }
}
