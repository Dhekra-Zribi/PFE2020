package com.smpp.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.smpp.demo.entities.Sms;

@Repository
public interface SmsRepository extends MongoRepository<Sms, Long> {
	
	List<Sms> findAllByTypeOrderByIdDesc(String type);
	List<Sms> deleteByType(String type);
	Long deleteSmsByType(String type);
	 
}