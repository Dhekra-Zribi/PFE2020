package com.smpp.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smpp.demo.entities.CountMsgDate;
@Repository
public interface CountMsgDateRepository extends MongoRepository<CountMsgDate, Long>{

	public CountMsgDate findByDate(String date);
}
