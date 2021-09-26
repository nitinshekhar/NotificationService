package com.nitin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.nitin.model.Notification;

@Service
public class NotificationOrderService {

	private Logger log = LoggerFactory.getLogger(NotificationOrderService.class);
	
	private final String notificationTopic = "Notification";
	
    @Autowired
    private KafkaTemplate<String, Notification> kafkaTemplate;

    public void sendMessage(Notification notification) {
        ListenableFuture<SendResult<String, Notification>> future = 
        		kafkaTemplate.send(notificationTopic, notification);
        log.info("Sent Message to kafka topic : " + notification.toString());
    }
}
