package com.smpp.demo.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smpp.demo.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

	public User findByEmailId(String emailId);
	public User findByEmailIdAndPassword(String emailId, String password);
	
	Optional<User> findByUserName(String username);

	  Boolean existsByUserName(String username);

	  Boolean existsByEmailId(String email);
}
