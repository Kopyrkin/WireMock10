package ru.tinkoff.myupgradeapplication.week6

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
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

    //Case 1
    @Test
    fun checkWikiContentDisplay() {
        stubFor(
            get(WireMock.urlPathMatching("/api.php"))
                .withQueryParam("action", WireMock.equalTo("query"))
                .withQueryParam("format", WireMock.equalTo("json"))
                .withQueryParam("list", WireMock.equalTo("search"))
                .withQueryParam("srsearch", WireMock.containing("Гуляй шальная императрица"))
                .willReturn(
                    ok(fileToString("mock/wiki_imperatrica.json"))
                )
        )
        with(StartPage()) {
            clickFirstButton()
        }
        with(WikiPage()) {
            replaceTextToWikiFiled(textToWikiSearch)
            clickWikiSearchButton()
        }
    }

    // Case 2
    @Test
    fun checkWikiContentSwitch() {
        val text = "Серега опытный мужик, он мне дает советов много"
        stubFor(
            get(WireMock.urlPathMatching("/api.php"))
                .inScenario("Serega")
                .whenScenarioStateIs(Scenario.STARTED)
                .willSetStateTo("Step 1 - Sapogi")
                .withQueryParam("action", WireMock.equalTo("query"))
                .withQueryParam("format", WireMock.equalTo("json"))
                .withQueryParam("list", WireMock.equalTo("search"))
                .withQueryParam(
                    "srsearch",
                    WireMock.containing(text)
                )
                .willReturn(
                    ok(fileToString("mock/wiki_serega1.json"))
                )
        )
        stubFor(
            get(WireMock.urlPathMatching("/api.php"))
                .inScenario("Serega")
                .whenScenarioStateIs("Step 1 - Sapogi")
                .willSetStateTo("Step finish")
                .withQueryParam("action", WireMock.equalTo("query"))
                .withQueryParam("format", WireMock.equalTo("json"))
                .withQueryParam("list", WireMock.equalTo("search"))
                .withQueryParam(
                    "srsearch",
                    WireMock.containing(text)
                )
                .willReturn(
                    ok(fileToString("mock/wiki_serega2.json"))
                )
        )
        with(StartPage()) {
            clickFirstButton()
        }
        with(WikiPage()) {
            replaceTextToWikiFiled(textToWikiSearchCase2Serega)
            clickWikiSearchButton()
            replaceTextToWikiFiled(textToWikiSearchCase2Serega)
            clickWikiSearchButton()
        }
    }

    // Case 3
    @Test
    fun checkRequestOnFirstPage() {
        stubFor(
            get(WireMock.urlPathMatching("/api.php"))
                .withQueryParam("action", WireMock.equalTo("query"))
                .withQueryParam("format", WireMock.equalTo("json"))
                .withQueryParam("list", WireMock.equalTo("search"))
                .withQueryParam("srsearch", WireMock.equalTo("Tinkoff Bank"))
        )
        with(StartPage()) {
            clickFirstButton()
        }
        with(WikiPage()) {
            replaceTextToWikiFiled(textToWikiSearchCase4TinkoffBank)
            clickWikiSearchButton()
        }

    }

    // Case 4
    @Test
    fun checkNoResultMessageDisplay() {
        val text = "Неудачные песни Пневмослона"
        stubFor(
            get(WireMock.urlPathMatching("/api.php"))
                .withQueryParam("action", WireMock.equalTo("query"))
                .withQueryParam("format", WireMock.equalTo("json"))
                .withQueryParam("list", WireMock.equalTo("search"))
                .withQueryParam("srsearch", WireMock.containing(text))
                .willReturn(
                    ok(fileToString("mock/wiki_no_results.json"))
                )
        )
        with(StartPage()) {
            clickFirstButton()
        }
        with(WikiPage()) {
            replaceTextToWikiFiled(textToWikiSearchCase3Slon)
            clickWikiSearchButton()
            Thread.sleep(4000)
        }
    }

    // Case 5 - 1
    @Test
    fun checkWikiResponseError401() {
        stubFor(
            get(WireMock.urlPathMatching("/api/"))
                .willReturn(
                    aResponse()
                        .withStatus(401)
                )
        )
        with(StartPage()) {
            clickShowPersonButton()
        }
    }

    // Case 5 - 2
    @Test
    fun checkWikiResponseError400() {
        stubFor(
            get(WireMock.urlPathMatching("/api/"))
                .willReturn(
                    aResponse()
                        .withStatus(400)
                )
        )
        with(StartPage()) {
            clickShowPersonButton()
        }
    }

    // Case 5 - 3
    @Test
    fun checkWikiResponseError500() {
        stubFor(
            get(WireMock.urlPathMatching("/api/"))
                .willReturn(
                    aResponse()
                        .withStatus(500)
                )
        )
        with(StartPage()) {
            clickShowPersonButton()
        }
    }
}

