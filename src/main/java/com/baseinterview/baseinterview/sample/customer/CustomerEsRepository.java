package com.baseinterview.baseinterview.sample.customer;

import com.baseinterview.baseinterview.sample.query.UserQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CustomerEsRepository {
    @Autowired
    private TransportClient transportClient;
    @Autowired
    private ObjectMapper objectMapper;

    public List<CustomerSearchResult> search(UserQuery query) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch("customer")
                .setTypes("customer")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchAllQuery())
                .setPostFilter(QueryBuilders.multiMatchQuery(query.getPhrase(), "name"))
                .setFrom(0).setSize(30).setExplain(true);
        log.debug("Searching " + searchRequestBuilder);
        SearchResponse response = searchRequestBuilder
                .get();
        SearchHits hits = response.getHits();
        List<CustomerSearchResult> results = new ArrayList<>();
        for (SearchHit searchHitFields : hits.getHits()) {
            try {
                results.add(objectMapper.readValue(searchHitFields.getSourceAsString(), CustomerSearchResult.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return results;
    }

    public void save(Customer customer) {
        try {
            transportClient.prepareIndex("customer", "customer")
                    .setSource(objectMapper.writeValueAsBytes(customer), XContentType.JSON)
                    .get();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
