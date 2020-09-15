package com.smpp.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigSmscRepo extends MongoRepository<ConfigSmsc, Long> {

}
