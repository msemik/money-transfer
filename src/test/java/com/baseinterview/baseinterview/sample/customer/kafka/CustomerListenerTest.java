package com.baseinterview.baseinterview.sample.customer.kafka;

import com.baseinterview.baseinterview.sample.customer.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerListenerTest {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private CustomerListener customerListener;

    @Test
    public void contextLoads() throws InterruptedException {
        ListenableFuture<SendResult<String, Customer>> future = kafkaTemplate
                .send(
                        "customer",
                        Customer.builder().name("Adam Mickiewicz").build());
        future.addCallback((sendResult) -> System.out.println("success"), (ex) -> System.out.println("failed"));
        System.out.println(Thread.currentThread().getId());
        assertThat(this.customerListener.countDownLatch1.await(60, TimeUnit.SECONDS)).isTrue();

    }

}