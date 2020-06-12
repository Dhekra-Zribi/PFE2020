package com.smpp.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smpp.demo.entities.Sms;

@Repository
public interface SmsRepository extends MongoRepository<Sms, Long> {
	

}
