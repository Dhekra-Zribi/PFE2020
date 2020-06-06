package com.smpp.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.smpp.demo.entities.Sms;

@Repository
public interface SmsRepository extends ReactiveMongoRepository<Sms, String> {
	
	

}
