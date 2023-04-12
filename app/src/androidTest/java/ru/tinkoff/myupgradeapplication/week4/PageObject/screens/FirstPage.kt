package ru.tinkoff.myupgradeapplication.week4.PageObject.screens

import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until

class FirstPage : BasePage(){

    val nextButtonSelector = By.text("Next")
    val changeButtonSelector = By.text("Change")

    fun pressNextButton() {
        device
            .wait(Until.findObject(nextButtonSelector), waitingTimeOut)
            .click()
    }

    fun checkTextOnScreen(firstText: String) {
        assert(
            device
                .wait(Until.hasObject(By.text(firstText)), waitingTimeOut)
        )
    }

    fun pressChangeButton() {
        device
            .wait(Until.findObject(changeButtonSelector), waitingTimeOut)
            .click()
    }

}
