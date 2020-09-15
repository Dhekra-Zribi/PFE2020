package com.smpp.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smpp.demo.entities.CountTr;



@Repository
public interface CountTrRepo extends MongoRepository<CountTr, Long>{

	public CountTr findByDate(String date);
	void deleteByDate(String date);
}
