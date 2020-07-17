package com.smpp.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smpp.demo.entities.Csv;
@Repository
public interface CsvRepository extends MongoRepository<Csv, String> {

}
