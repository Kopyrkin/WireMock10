package ru.tinkoff.myupgradeapplication.week5.kautomator

import android.util.Log
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kaspersky.components.alluresupport.interceptors.step.AllureMapperStepInterceptor
import com.kaspersky.components.alluresupport.withAllureSupport
import com.kaspersky.kaspresso.interceptors.watcher.testcase.impl.views.DumpViewsInterceptor
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.params.ScreenshotParams
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.myupgradeapplication.MainActivity
import ru.tinkoff.myupgradeapplication.week5.kaspressoBuilder

class KAutomatorTest :TestCase(kaspressoBuilder) {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun kautomatorTest() = run {

        val loginValue = "Tinkoff"
        val passwordValue = "Upgrade"

        step("First Step") {
            KautomatorFirstScreen {
                nextButton.click()
            }
        }
        step("Second Step") {

            KautomatorLoginScreen {
                login.replaceText(loginValue)
                password.replaceText(passwordValue)
                submit.click()

                snack.hasText("You enter login = $loginValue password = $passwordValue")
            }
        }
    }
}

const val PACKAGE : String = "ru.tinkoff.myupgradeapplication"

