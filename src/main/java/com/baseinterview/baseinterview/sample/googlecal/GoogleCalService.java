package com.baseinterview.baseinterview.sample.googlecal;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GoogleCalService {
    private RestTemplate restTemplate;
    private String url;

    public GoogleCalService(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public List<String> search(String keywords) {
        return restTemplate.exchange(
                url + "/" + keywords, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<String>>() {
                }).getBody();
    }
}
