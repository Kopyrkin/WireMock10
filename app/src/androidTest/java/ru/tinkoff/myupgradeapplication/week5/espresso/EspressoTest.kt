package ru.tinkoff.myupgradeapplication.week5.espresso

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.myupgradeapplication.MainActivity
import ru.tinkoff.myupgradeapplication.R
import ru.tinkoff.myupgradeapplication.week5.espresso.screens.EspressoFirstScreen
import ru.tinkoff.myupgradeapplication.week5.espresso.screens.EspressoLoginScreen

@RunWith(AndroidJUnit4::class)
class EspressoTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun fragmentNavigationTest() {
        EspressoFirstScreen().clickFirstButton()
        EspressoLoginScreen().clickPreviousButton()
        EspressoFirstScreen().checkScreenTitile("First Fragment")
    }


    @Test
    fun checkFabNotification(){
        with(EspressoFirstScreen()){
            clickFab()
            checkTextOnSnackBar("Replace with your own action")
        }
    }

    @Test
    fun myClassMethod_ReturnsTrue() {
        val loginValue = "Tinkoff"
        val passwordValue = "Upgrade"
        EspressoFirstScreen().clickFirstButton()
        with (EspressoLoginScreen()) {
            typeTextToLoginFiled(loginValue)
            typeTextToPasswordFiled(passwordValue)
            clickSubmitButton()
            checkTextOnSnackBar("You enter login = $loginValue password = $passwordValue")
        }
    }


    @Test
    fun checkHintColor(){

        EspressoFirstScreen().clickFirstButton()
        with (EspressoLoginScreen()) {
            clickSubmitButton()
            checkLoginHintColor(R.color.error_hint)
        }
    }

}
