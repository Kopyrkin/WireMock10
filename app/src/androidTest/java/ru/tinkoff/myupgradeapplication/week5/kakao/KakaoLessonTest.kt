package ru.tinkoff.myupgradeapplication.week5

import androidx.test.ext.junit.rules.ActivityScenarioRule
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KSnackbar
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.myupgradeapplication.MainActivity
import ru.tinkoff.myupgradeapplication.R
import ru.tinkoff.myupgradeapplication.week5.espresso.screens.EspressoFirstScreen
import ru.tinkoff.myupgradeapplication.week5.espresso.screens.EspressoLoginScreen
import ru.tinkoff.myupgradeapplication.week5.kakao.screens.KakaoFirstScreen
import ru.tinkoff.myupgradeapplication.week5.kakao.screens.KakaoLoginScreen

class KakaoLessonTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun fragmentNavigationTest() {
        Screen.onScreen<KakaoFirstScreen> {
            nextButton.click()
        }
        Screen.onScreen<KakaoLoginScreen> {
            buttonPrevious.click()
        }
        Screen.onScreen<KakaoFirstScreen> {
            screenTitle.hasText("First Fragment")
        }
    }




    @Test
    fun checkFabNotification(){
        Screen.onScreen<KakaoFirstScreen> {
            fab.click()
            textOnSnackBar.hasText("Replace with your own action")
        }
    }

    @Test
    fun myClassMethod_ReturnsTrue() {
        val loginValue = "Tinkoff"
        val passwordValue = "Upgrade"

        Screen.onScreen<KakaoFirstScreen> {
            nextButton.click()
        }
        Screen.onScreen<KakaoLoginScreen> {
            editTextlogin.typeText(loginValue)
            editTextpassword.typeText(passwordValue)
            buttonSubmit.click()
            textOnSnackBar.hasText("You enter login = $loginValue password = $passwordValue")
        }
    }


    @Test
    fun checkHintColor(){
        Screen.onScreen<KakaoFirstScreen> {
            nextButton.click()
        }
        Screen.onScreen<KakaoLoginScreen> {
            buttonSubmit.click()
            checkLoginFieldHintColor(R.color.error_hint)
        }
    }
}
