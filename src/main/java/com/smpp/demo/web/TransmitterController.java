package com.smpp.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smpp.demo.entities.Sms;
import com.smpp.demo.services.TransmitterService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(value="transmitter")
@RestController
public class TransmitterController {
	
	
	TransmitterService service;
	
	
	/*public Mono<Void>bind(){
		
		 service.bindToSmscTransmitter();
		 
	}*/
	
	
    @PostMapping(value = "/send",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
 
	public Mono<Sms>sendOnemessage(@PathVariable String shortMessage, @PathVariable String sourceAddress,@PathVariable String destinationAddress  ){
		
		service.bindToSmscTransmitter();
		
		Sms sms=new Sms();
		sms.setShortMessage(shortMessage);
		sms.setSourceAddr(sourceAddress);
		sms.setDestAddr(destinationAddress);
		 service.sendSingleSMS(shortMessage, sourceAddress, destinationAddress);
		return service.create(sms) ;
	}
    
    
   
	
	
	/*public Flux<Sms>sendMessage(){
		
		return service.sendSMS();
	}*/
	

}
