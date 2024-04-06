package ru.tinkoff.myupgradeapplication.week4.PageObject.screens

import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until

class LoginPage: BasePage() {

    val loginFiledSelector = By.res("ru.tinkoff.myupgradeapplication:id/edittext_login")
    val passwordFiledSelector = By.res("ru.tinkoff.myupgradeapplication:id/edittext_password")
    val submitButtonSelector = By.res("ru.tinkoff.myupgradeapplication:id/button_submit")


    fun enterLogin(loginValue: String) {
        device
            .wait(Until.findObject(loginFiledSelector), waitingTimeOut)
            .text = loginValue
    }

    fun enterPassword(passwordValue: String) {
        device
            .wait(Until.findObject(passwordFiledSelector), waitingTimeOut)
            .text = passwordValue
    }

    fun checkTextOnSnackBar(text: String) {
        assert(device.wait(Until.hasObject(By.text(text)), waitingTimeOut))
    }

    fun pressSubmitButton() {
        device
            .wait(Until.findObject(submitButtonSelector), waitingTimeOut)
            .click()
    }

}
