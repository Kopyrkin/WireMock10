package ru.tinkoff.myupgradeapplication.week4.PageObject

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.myupgradeapplication.MainActivity
import ru.tinkoff.myupgradeapplication.week4.PageObject.screens.FirstPage
import ru.tinkoff.myupgradeapplication.week4.PageObject.screens.LoginPage
import ru.tinkoff.myupgradeapplication.R

class UserFlowTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun enterLoginPasswordTest(){
        val loginValue = "Tinkoff"
        val passwordValue = "Upgrade"
        with(FirstPage()){
            pressNextButton()
        }
        with(LoginPage()){
            enterLogin(loginValue)
            enterPassword(passwordValue)
            pressSubmitButton()
            checkTextOnSnackBar("You enter login = $loginValue password = $passwordValue")
        }
    }


    @Test
    fun checkSwitchingTextTest(){

        val firstText = InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.first_text)
        val secondText = "qwertyuio" //InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.second_text)

        with(FirstPage()){
            checkTextOnScreen(firstText)
            pressChangeButton()
            checkTextOnScreen(secondText)
        }
    }

}