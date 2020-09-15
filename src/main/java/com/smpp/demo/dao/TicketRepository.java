package com.smpp.demo.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smpp.demo.entities.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String>{
	
	public List<Ticket> findByCreatorOrderByIdDesc(String creator);

}