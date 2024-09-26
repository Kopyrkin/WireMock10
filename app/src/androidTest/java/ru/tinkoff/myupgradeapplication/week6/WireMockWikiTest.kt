package ru.tinkoff.myupgradeapplication.week6

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomakehurst.wiremock.stubbing.Scenario
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import ru.tinkoff.myupgradeapplication.MainActivity
import ru.tinkoff.myupgradeapplication.week6.rules.LocalhostPreferenceRule
import ru.tinkoff.myupgradeapplication.week6.screens.StartPage
import ru.tinkoff.myupgradeapplication.week6.screens.WikiPage
import ru.tinkoff.myupgradeapplication.week6.utils.fileToString

class WireMockWikiTest {
    @get: Rule
    val ruleChain: RuleChain = RuleChain.outerRule(LocalhostPreferenceRule())
        .around(WireMockRule(5000))
        .around(ActivityScenarioRule(MainActivity::class.java))

    @Test
    fun firstMockTest() {
        stubFor(
            get(WireMock.urlPathMatching("/api.php"))
                .withQueryParam("action", WireMock.equalTo("query"))
                .withQueryParam("format", WireMock.equalTo("json"))
                .withQueryParam("list", WireMock.equalTo("search"))
                .withQueryParam("srsearch", WireMock.containing("Oleg"))
                .willReturn(
                    ok(fileToString("mock/wiki.json"))
                )
        )
        with(StartPage()) {
            clickFirstButton()
        }
        with(WikiPage()) {
            typeTextToWikiFiled("Oleg")
            clickWikiSearchButton()
        }
        Thread.sleep(15000)
    }

    @Test
    fun bigChain() {
        stubFor(
            get(urlEqualTo("/api/"))
                .inScenario("Films")
                .whenScenarioStateIs(Scenario.STARTED)
                .willSetStateTo("Step 1 - Tarantino")
                .willReturn(
                    WireMock.ok(fileToString("mock/mock-first.json"))
                )
        )

        stubFor(
            get(WireMock.urlPathMatching("/api.php"))
                .withQueryParam("action", WireMock.equalTo("query"))
                .withQueryParam("format", WireMock.equalTo("json"))
                .withQueryParam("list", WireMock.equalTo("search"))
                .withQueryParam("srsearch", WireMock.containing("Oleg"))
                .inScenario("Films")
                .whenScenarioStateIs("Step 1 - Tarantino")
                .willSetStateTo("Step 2 - Oleg")
                .willReturn(
                    ok(fileToString("mock/wiki.json"))
                )
        )

        stubFor(
            get(urlEqualTo("/api/"))
                .inScenario("Films")
                .whenScenarioStateIs("Step 2 - Oleg")
                .willSetStateTo("Step finish")
                .willReturn(
                    WireMock.ok(fileToString("mock/mock-second.json"))
                )
        )

        with(StartPage()) {
            clickShowPersonButton()
            clickFirstButton()
        }
        with(WikiPage()) {
            typeTextToWikiFiled("Oleg")
            clickWikiSearchButton()
            Thread.sleep(4000)
            clickPreviousButton()
        }
        with(StartPage()) {
            clickShowPersonButton()
            Thread.sleep(4000)
        }
    }
}

