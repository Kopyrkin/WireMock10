package ru.tinkoff.myupgradeapplication.week6

import android.content.Context
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.any
import com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.containing
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import com.github.tomakehurst.wiremock.client.WireMock.equalToXml
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.matching
import com.github.tomakehurst.wiremock.client.WireMock.matchingXPath
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.okJson
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.unauthorized
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.myupgradeapplication.MainActivity


@RunWith(AndroidJUnit4::class)
class WireMockTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val mockRule = WireMockRule(5000)

    @Test
    fun exactUrlOnly() {

        stubFor(
            get(urlEqualTo("/some/thing"))
                .willReturn(
                    aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Hello world!")
                )
        )

    }

    @Test
    fun fragmentNavigationTest() {
        setStringPrefParam("demo_url", "url", "http://localhost:5000/w/")

        // just examples:
        val wikiMatcher: MappingBuilder = get(WireMock.urlPathMatching("/w/api.php"))
            .withQueryParam("action", equalTo("query"))
            .withQueryParam("format", equalTo("json"))
            .withQueryParam("list", equalTo("search"))
            .withQueryParam("srsearch", equalTo("tinkoff"))

        stubFor(
            wikiMatcher
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody("body values")
                )
        )

        stubFor(
            any(urlEqualTo("/auth/step?user=billy"))
                .withHeader("Accept", containing("xml"))
                .withCookie("session", matching(".*12345.*"))
                .withQueryParam("term", equalTo("WireMock"))
                .withRequestBody(equalToXml("<results/>"))
                .withRequestBody(equalToJson("{name=billy;}"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                )
        )

        verify(
            anyRequestedFor(
                urlEqualTo("/auth/step?user=billy")
            )
                .withHeader("Accept", containing("xml"))
                .withCookie("session", matching(".*12345.*"))
                .withQueryParam("term", equalTo("WireMock"))
                .withRequestBody(equalToXml("<results/>"))
                .withRequestBody(equalToJson("{name=billy;}"))
        )

        stubFor(
            post(
                urlEqualTo("/client/checkCardValue")
            )
                .inScenario("Check debit card state")
                .whenScenarioStateIs(STARTED)
                .willSetStateTo("Step 1")
                .willReturn(okJson(" { cardValue = 400 }"))
        )

        stubFor(
            post(
                urlEqualTo("/client/pay?item=tax")
            )
                .inScenario("Check debit card state")
                .whenScenarioStateIs("Step 1")
                .willSetStateTo("Step 2")
                .willReturn(ok())
        )

        stubFor(
            post(
                urlEqualTo("/client/checkCardValue")
            )
                .inScenario("Check debit card state")
                .whenScenarioStateIs("Step 1")
                .willSetStateTo(STARTED)
                .willReturn(okJson(" { cardValue = 200 }"))
        )

        stubFor(
            wikiMatcher.willReturn(
                aResponse()
                    .withStatus(200)
            )
        )
        stubFor(
            any(urlEqualTo("/auth/step"))
                .withQueryParam("search_term", equalTo("WireMock"))
                .withBasicAuth("jeff@example.com", "jeffteenjefftyjeff")
                .withRequestBody(equalToXml("<search-results />"))
                .withRequestBody(matchingXPath("//search-results"))
                .withRequestBody(equalToJson("{ name = billy; }", true, true))
                .willReturn(
                    unauthorized()
                )
        )
    }

    private fun setStringPrefParam(prefName: String, param: String, value: String) {
        val pref = InstrumentationRegistry.getInstrumentation().targetContext.getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putString(param, value)
        editor.commit()
    }
}

