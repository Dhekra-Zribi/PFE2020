package com.smpp.demo.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smpp.demo.entities.Epriorities;
import com.smpp.demo.entities.Priorities;

@Repository
public interface PrioritiesRepository extends MongoRepository<Priorities, String> {

	Optional<Priorities> findByName(Epriorities name);
}
