package com.baseinterview.baseinterview.sample;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticSearchConfiguration {
    private TransportClient client;

    @Bean
    @Primary
    synchronized TransportClient createTransportClient() {
        try {
            if (client == null) {
                client = new PreBuiltTransportClient(Settings.EMPTY)
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            }
            return client;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("hello");
        if (client != null) {
            client.close();
        }
    }

}
