package com.nitin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nitin.common.CommunicationMode;
import com.nitin.common.Status;
import com.nitin.model.Notification;
import com.nitin.service.NotificationOrderService;

@RestController
@RequestMapping (value="/kafka")

public class NotificationController {

	@Autowired
	private NotificationOrderService service;
	
	@GetMapping(value="/publish")
	public String sendMessageToKafkaTopic (@RequestParam ("id") String participantId,
											@RequestParam ("type") String notificationType,
											@RequestParam("status") Status status) {
		Notification notification = new Notification(participantId,notificationType,status);
		notification.setCommunicationMode(getCommunicationPreference(participantId));
		service.sendMessage(notification);
		return "Message sent to the Kafka Topic java_in_use_topic Successfully with status " +notificationType ;		
	}
	
	private static CommunicationMode getCommunicationPreference(String ParticipantId) {
		final String URI = "http://localhost:9011/participant/1";
		RestTemplate restTemplate = new RestTemplate();
		CommunicationMode mode = restTemplate.getForObject(URI, CommunicationMode.class);
		return mode;
	}
}
