package com.smpp.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smpp.demo.entities.Commentaire;
@Repository
public interface CommentaireRepository extends MongoRepository<Commentaire, String> {

	Optional<Commentaire> findByComment(String comment);
	
	public List<Commentaire> findByTicket(String ticket);
	void deleteByComment(String comment);
}
