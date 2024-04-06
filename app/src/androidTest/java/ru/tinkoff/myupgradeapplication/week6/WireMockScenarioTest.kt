package ru.tinkoff.myupgradeapplication.week6

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import ru.tinkoff.myupgradeapplication.MainActivity
import ru.tinkoff.myupgradeapplication.week6.rules.LocalhostPreferenceRule
import ru.tinkoff.myupgradeapplication.week6.screens.StartPage
import ru.tinkoff.myupgradeapplication.week6.utils.fileToString

class WireMockScenarioTest {
    @get: Rule
    val ruleChain: RuleChain = RuleChain.outerRule(LocalhostPreferenceRule())
        .around(WireMockRule(5000))
        .around(ActivityScenarioRule(MainActivity::class.java))

    @Test
    fun testWithChains() {

        stubFor(
            get(urlEqualTo("/api/"))
                .inScenario("Films")
                .whenScenarioStateIs(STARTED)
                .willSetStateTo("Step 1 - Tarantino")
                .willReturn(
                    WireMock.ok(fileToString("mock/mock-first.json"))
                )
        )
        stubFor(
            get(urlEqualTo("/api/"))
                .inScenario("Films")
                .whenScenarioStateIs("Step 1 - Tarantino")
                .willSetStateTo("Step finish")
                .willReturn(
                    WireMock.ok(fileToString("mock/mock-second.json"))
                )
        )

        with(StartPage()) {
            clickShowPersonButton()
            Thread.sleep(2000)
            clickShowPersonButton()
            Thread.sleep(2000)
        }
    }
}

