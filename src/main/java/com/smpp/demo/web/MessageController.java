package com.smpp.demo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smpp.demo.dao.SmsRepository;
import com.smpp.demo.entities.Sms;
import com.smpp.demo.services.MessageService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequestMapping(value = "/message")
public class MessageController {

	
	
	
	
	@Autowired
	private MessageService messageService;
	
	
	@GetMapping("/messages")
    public Flux<Sms> getAllMessages() {
        return messageService.findAll();
    }
    
    
    @PostMapping("/message/create")
    public ResponseEntity<Mono<@Valid Sms>>  createMessage(@Valid @RequestBody Sms message) {
        return ResponseEntity.status(HttpStatus.CREATED) 
        		.body(messageService.create(message)); 
        
    }
    
    
    @PostMapping("/message/create1")
    public Mono<Sms> create(@RequestBody Sms msg) {
        return messageService.create(msg);
    }
    
    @GetMapping("/message/{id}")
    public Mono<ResponseEntity<Sms>> getMessageById(@PathVariable(value = "id") String msgid) {
        return messageService.findById(msgid)
                .map(savedMsg -> ResponseEntity.ok(savedMsg))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
   /* @PutMapping("/msg/{id}")
    public Mono<ResponseEntity<Sms>> updateTweet(@PathVariable(value = "id") String smsId,
                                                   @Valid @RequestBody Sms sms) {
        return MessageService.findById(smsId)
                .flatMap(existingMessage -> {
                    existingMessage.setShortMessage(sms.getShortMessage());
                    return repository.save(existingMessage);
                })
                .map(updatedMessage -> new ResponseEntity<>(updatedMessage, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/
    
}
