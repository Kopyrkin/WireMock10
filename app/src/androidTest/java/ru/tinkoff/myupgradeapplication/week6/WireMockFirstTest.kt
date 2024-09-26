package ru.tinkoff.myupgradeapplication.week6

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.http.Fault
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import ru.tinkoff.myupgradeapplication.MainActivity
import ru.tinkoff.myupgradeapplication.week6.rules.LocalhostPreferenceRule
import ru.tinkoff.myupgradeapplication.week6.screens.StartPage
import ru.tinkoff.myupgradeapplication.week6.utils.fileToString

class WireMockFirstTest {
    @get: Rule
    val ruleChain: RuleChain = RuleChain.outerRule(LocalhostPreferenceRule())
        .around(WireMockRule(5000))
        .around(ActivityScenarioRule(MainActivity::class.java))

    @Test
    fun emptyWireMockTest() {
        // то что тест запускается - уже победа
    }

    @Test
    fun firstMockTest() {
        stubFor(
            get(urlEqualTo("/api/"))
                .willReturn(
                    ok(fileToString("mock/mock-first.json"))
                        .withFault(Fault.EMPTY_RESPONSE)
                )
        )

        with(StartPage()) {
            clickShowPersonButton()
            Thread.sleep(5000)
            with(personView) {
                checkEmail("joona.haataja@example.com")
                checkFio("Mr Quentin Tarantino")
            }
        }
        Thread.sleep(5000) // sorry for this shit
    }

    @Test
    fun testNeedChains() {
        stubFor(
            get(urlEqualTo("/api/"))
                .willReturn(
                    ok(fileToString("mock/mock-first.json"))
                )
        )
        stubFor(
            get(urlEqualTo("/api/"))
                .willReturn(
                    //ok(fileToString("mock/mock-second.json"))
                    aResponse()
                        .withStatus(200)
                        .withBody(fileToString("mock/mock-second.json"))

                )
        )

        with(StartPage()) {
            clickShowPersonButton()
            Thread.sleep(5000)
            clickShowPersonButton()
            Thread.sleep(5000)

        }
    }
}

