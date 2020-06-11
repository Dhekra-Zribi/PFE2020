package com.smpp.demo.web;

import java.io.IOException;

import javax.validation.Valid;

import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.ValueNotSetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.smpp.demo.entities.Sms;
import com.smpp.demo.services.TransmitterService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class TransmitterController {
	
	
	TransmitterService service;
	
	
	/*public Mono<Void>bind(){
		
		 service.bindToSmscTransmitter();
		 
	}*/
	
/*	@PostMapping("/message")
    public ResponseEntity<Mono<@Valid Sms>>createMessage(@PathVariable String shortMessage, @PathVariable String sourceAddress,@PathVariable String destinationAddress  ) throws ValueNotSetException, TimeoutException, PDUException, WrongSessionStateException, IOException {
		Sms sms=new Sms();
		sms.setShortMessage(shortMessage);
		sms.setSourceAddr(sourceAddress);
		sms.setDestAddr(destinationAddress);
        return ResponseEntity.status(HttpStatus.CREATED) 
        		.body(service.create(sms));
    }
	
    @PostMapping(value = "/send",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
 
	public Mono<Sms>sendOnemessage(@PathVariable String shortMessage, @PathVariable String sourceAddress,@PathVariable String destinationAddress  ) throws ValueNotSetException, TimeoutException, PDUException, WrongSessionStateException, IOException{
		
    	
    	
        
		
		Sms sms=new Sms();
		sms.setShortMessage(shortMessage);
		sms.setSourceAddr(sourceAddress);
		sms.setDestAddr(destinationAddress);
		 service.sendSingleSMS(shortMessage, sourceAddress, destinationAddress);
		return service.create(sms) ;
	}
    */
    
   
	
	
	/*public Flux<Sms>sendMessage(){
		
		return service.sendSMS();
	}*/
	

}
