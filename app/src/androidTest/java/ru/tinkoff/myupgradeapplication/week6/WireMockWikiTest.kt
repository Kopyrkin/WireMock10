package ru.tinkoff.myupgradeapplication.week6

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomakehurst.wiremock.stubbing.Scenario
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import ru.tinkoff.myupgradeapplication.MainActivity
import ru.tinkoff.myupgradeapplication.week6.rules.LocalhostPreferenceRule
import ru.tinkoff.myupgradeapplication.week6.screens.StartPage
import ru.tinkoff.myupgradeapplication.week6.screens.WikiPage
import ru.tinkoff.myupgradeapplication.week6.utils.fileToString
import kotlin.concurrent.thread

class WireMockWikiTest {
    @get: Rule
    val ruleChain: RuleChain =
        RuleChain.outerRule(LocalhostPreferenceRule()).around(WireMockRule(5000))
            .around(ActivityScenarioRule(MainActivity::class.java))

    @Test
    fun firstMockTest() {
        stubFor(
            get(urlPathMatching("/api.php")).withQueryParam(
                "action", WireMock.equalTo("query")
            ).withQueryParam("format", WireMock.equalTo("json"))
                .withQueryParam("list", WireMock.equalTo("search"))
                .withQueryParam("srsearch", WireMock.containing("Oleg")).willReturn(
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
    }

    @Test
    fun bigChain() {
        stubFor(
            get(urlEqualTo("/api/")).inScenario("Films").whenScenarioStateIs(Scenario.STARTED)
                .willSetStateTo("Step 1 - Tarantino").willReturn(
                    ok(fileToString("mock/mock-first.json"))
                )
        )

        stubFor(
            get(urlPathMatching("/api.php")).withQueryParam(
                "action", WireMock.equalTo("query")
            ).withQueryParam("format", WireMock.equalTo("json"))
                .withQueryParam("list", WireMock.equalTo("search"))
                .withQueryParam("srsearch", WireMock.containing("Oleg")).inScenario("Films")
                .whenScenarioStateIs("Step 1 - Tarantino").willSetStateTo("Step 2 - Oleg")
                .willReturn(
                    ok(fileToString("mock/wiki.json"))
                )
        )

        stubFor(
            get(urlEqualTo("/api/")).inScenario("Films").whenScenarioStateIs("Step 2 - Oleg")
                .willSetStateTo("Step finish").willReturn(
                    ok(fileToString("mock/mock-second.json"))
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

    //Case 1
    @Test
    fun checkWikiContentDisplay() {
        val textRequest = "Гуляй шальная императрица"
        stubFor(
            get(urlPathMatching("/api.php")).withQueryParam(
                "srsearch", WireMock.containing(textRequest)
            ).willReturn(
                ok(fileToString("mock/wiki_imperatrica.json"))
            )
        )
        with(StartPage()) {
            clickFirstButton()
        }
        with(WikiPage()) {
            replaceTextToWikiFiled(textRequest)
            clickWikiSearchButton()
            verify(
                getRequestedFor(urlPathMatching("/api.php")).withQueryParam(
                    "srsearch", WireMock.containing(textRequest)
                )
            )
            checkWikiResult(textToWikiSearchCaseImperatrica)
        }
    }

    // Case 2
    @Test
    fun checkWikiContentSwitch() {
        val text = "Серега опытный мужик, он мне дает советов много"
        stubFor(
            get(urlPathMatching("/api.php")).inScenario("Serega")
                .whenScenarioStateIs(Scenario.STARTED).willSetStateTo("Step 1 - Sapogi")
                .withQueryParam(
                    "srsearch", WireMock.containing(text)
                ).willReturn(
                    ok(fileToString("mock/wiki_serega1.json"))
                )
        )
        stubFor(
            get(urlPathMatching("/api.php")).inScenario("Serega")
                .whenScenarioStateIs("Step 1 - Sapogi").willSetStateTo("Step finish")
                .withQueryParam(
                    "srsearch", WireMock.containing(text)
                ).willReturn(
                    ok(fileToString("mock/wiki_serega2.json"))
                )
        )
        with(StartPage()) {
            clickFirstButton()
        }
        with(WikiPage()) {
            replaceTextToWikiFiled(text)
            clickWikiSearchButton()
            checkWikiResult(textToWikiSearchCaseSeregaFirstAnswer)
            verify(
                getRequestedFor(urlPathMatching("/api.php")).withQueryParam(
                    "srsearch", WireMock.containing(text)
                )
            )

            replaceTextToWikiFiled(text)
            clickWikiSearchButton()
            checkWikiResult(textToWikiSearchCaseSeregaNextAnswer)
            verify(
                getRequestedFor(urlPathMatching("/api.php")).withQueryParam(
                    "srsearch", WireMock.containing(text)
                )
            )
        }
    }

    // Case 3
    @Test
    fun checkRequestOnFirstPage() {
        val nameOfBank = "Tinkoff Bank"
        stubFor(
            get(urlPathMatching("/api.php")).withQueryParam(
                "srsearch", WireMock.equalTo(nameOfBank)
            )
        )
        with(StartPage()) {
            clickFirstButton()
        }
        with(WikiPage()) {
            replaceTextToWikiFiled(nameOfBank)
            clickWikiSearchButton()
            verify(
                getRequestedFor(urlPathMatching("/api.php")).withQueryParam(
                    "srsearch", WireMock.containing(nameOfBank)
                )
            )
        }
    }

    // Case 4
    @Test
    fun checkNoResultMessageDisplay() {
        val text = "Неудачные песни Пневмослона"
        stubFor(
            get(urlPathMatching("/api.php")).withQueryParam(
                "srsearch", WireMock.containing(text)
            ).willReturn(
                ok(fileToString("mock/wiki_no_results.json"))
            )
        )
        with(StartPage()) {
            clickFirstButton()
        }
        with(WikiPage()) {
            replaceTextToWikiFiled(text)
            clickWikiSearchButton()
            checkWikiResult(textToWikiSearchCaseSlon)
            verify(
                getRequestedFor(urlPathMatching("/api.php")).withQueryParam(
                    "srsearch", WireMock.containing(text)
                )
            )
        }
    }

    // Case 5 - 1
    @Test
    fun checkWikiResponseError401() {
        stubFor(
            get(urlPathMatching("/api/")).willReturn(
                aResponse().withStatus(401)
            )
        )

        with(StartPage()) {
            clickShowPersonButton()
            val lastServerEvent = WireMock.getAllServeEvents().lastOrNull()
            assertNotNull(lastServerEvent)
            val status = lastServerEvent?.responseDefinition?.status
            assertEquals(401, status)
        }
    }

    // Case 5 - 2
    @Test
    fun checkWikiResponseError400() {
        stubFor(
            get(urlPathMatching("/api/")).willReturn(
                aResponse().withStatus(400)
            )
        )
        with(StartPage()) {
            clickShowPersonButton()
        }
        val lastServerEvent = WireMock.getAllServeEvents().lastOrNull()
        assertNotNull(lastServerEvent)
        val status = lastServerEvent?.responseDefinition?.status
        assertEquals(400, status)
    }

    // Case 5 - 3
    @Test
    fun checkWikiResponseError500() {
        stubFor(
            get(urlPathMatching("/api/")).willReturn(
                aResponse().withStatus(500)
            )
        )
        with(StartPage()) {
            clickShowPersonButton()
            val lastServerEvent = WireMock.getAllServeEvents().lastOrNull()
            assertNotNull(lastServerEvent)
            val status = lastServerEvent?.responseDefinition?.status
            assertEquals(500, status)
        }
    }
}

