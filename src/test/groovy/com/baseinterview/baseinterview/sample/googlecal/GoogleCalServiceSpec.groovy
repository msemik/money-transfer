package com.baseinterview.baseinterview.sample.googlecal

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
class GoogleCalServiceSpec extends Specification {
    @Rule
    WireMockRule wireMockRule = new WireMockRule(8089)
    @Autowired
    RestTemplate restTemplate

    def "search"() {
        given:
        def service = new GoogleCalService(restTemplate, "http://localhost:8089/ctx")
        wireMockRule.
                stubFor(get(urlEqualTo("/ctx/asd"))
                        .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody('["a", "b", "c"]')))
        when:
        def searchResult = service.search("asd")

        then:
        searchResult.get(0) != null
        searchResult.size() == 3
    }
}
