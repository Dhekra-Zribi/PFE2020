package com.smpp.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smpp.demo.entities.Sms;
import com.smpp.demo.services.TransceiverService;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value="transceiver")
@RestController
public class TransceiverController {

	@Autowired
	TransceiverService service;
	
	
	  @Bean
	@PostMapping(value = "/send",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public void sendMessage(@PathVariable String shortMessage, @PathVariable String sourceAddress,@PathVariable String destinationAddress) {
		  service.bindToSmscTransciever();
		 service.transcieveSms(shortMessage,sourceAddress,destinationAddress);
	}
	
}
