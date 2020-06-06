package com.smpp.demo.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.smpp.demo.dao.SmsRepository;
import com.smpp.demo.entities.Sms;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageService {
	
	  
@Autowired
    SmsRepository repository;
 
    public  Mono<Sms> create(@Valid @RequestBody Sms message) {
        
		return (Mono<Sms>) repository.save(message).subscribe();
    }
 
    public Mono<Sms> findById(String id) {
        return repository.findById(id);
    }
 
   
 
    public Flux<Sms> findAll() {
        return repository.findAll();
    }
 
    public Mono<Sms> update(Sms message) {
        return repository.save(message);
    }
 
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
 
}
