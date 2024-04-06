package ru.tinkoff.myupgradeapplication.week4

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.myupgradeapplication.MainActivity

@RunWith(AndroidJUnit4::class)
class ActivityScenarioRuleTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun myClassMethod_ReturnsTrue() {
        println()
    }
}
