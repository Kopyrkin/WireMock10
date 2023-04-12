package ru.tinkoff.myupgradeapplication.week4

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.myupgradeapplication.MainActivity

@RunWith(AndroidJUnit4::class)
class UiAutomatorSimpleTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    val device = UiDevice.getInstance(getInstrumentation())
    val waitingTimeOut = 2000L

    @Test
    fun fragmentNavigationTest() {
        device
            .wait(Until.findObject(By.text("Next")), waitingTimeOut)
            .click()
        device
            .wait(Until.findObject(By.text("Previous")), waitingTimeOut)
            .click()
        device
            .wait(Until.hasObject(By.text("First Fragment")), waitingTimeOut)
    }


    @Test
    fun notificationShowingTest(){
        device
            .wait(Until.findObject(By.res("ru.tinkoff.myupgradeapplication:id/fab")), waitingTimeOut)
            .click()
        device
            .wait(Until.hasObject(By.text("Replace with your own action")), waitingTimeOut)
    }
}