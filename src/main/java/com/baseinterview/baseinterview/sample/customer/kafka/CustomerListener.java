package com.baseinterview.baseinterview.sample.customer.kafka;

import com.baseinterview.baseinterview.sample.customer.Customer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class CustomerListener {

    public final CountDownLatch countDownLatch1 = new CountDownLatch(1);

    @KafkaListener(id = "customerListener", topics = "customer", groupId = "${kafka.groupId}")
    public void listen(ConsumerRecord<String, Customer> record, Acknowledgment ack) {
        try {
            System.out.println(record);
            countDownLatch1.countDown();
        } finally {
            ack.acknowledge();
        }
    }

}