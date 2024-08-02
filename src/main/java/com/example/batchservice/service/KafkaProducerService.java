package com.example.batchservice.service;

import com.example.batchservice.constants.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendDailyStockDataMessage(String message) {
        kafkaTemplate.send(KafkaConstants.DAILY_DATA_REQUEST_TOPIC, message);
    }
}
