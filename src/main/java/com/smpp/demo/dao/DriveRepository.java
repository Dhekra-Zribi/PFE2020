package com.smpp.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smpp.demo.entities.Drive;

@Repository
public interface DriveRepository extends MongoRepository<Drive, String> {
	Optional<Drive> findByNameAndUser(String name, String user);
	Boolean existsByNameAndUser(String name, String user);
	Boolean existsByUser(String user);
	public List<Drive> findByUserOrderByIdDesc(String user);
	void deleteByNameAndUser(String name, String user);

}
