package com.baseinterview.baseinterview.sample.customer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import spock.lang.Specification

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CustomerControllerSpec extends Specification {
    @LocalServerPort
    int port

    @Autowired
    private TestRestTemplate template

    void setup() {
    }

    def "create customer"() {
        given:
        def body = '{"name" : "xardass", "age" : 12}'
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        HttpEntity<String> entity = new HttpEntity<String>(body, headers)


        when:
        def response = template
                .postForEntity("http://localhost:{port}/sample/customer", entity, Customer.class, port)
        then:
        response.getStatusCode() == HttpStatus.OK
        response.body.name == "xardass"
        response.body.age == 12
        response.body.id >= 1
    }

    def "find all customers"() {
        when:
        def response = template.exchange(
                "http://localhost:{port}/sample/customer",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Customer>>() {},
                port)


        then:
        response.getStatusCode() == HttpStatus.OK
        response.body.get(response.body.size()-1).name == 'xardass'

    }

}