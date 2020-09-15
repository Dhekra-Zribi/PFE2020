package com.smpp.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Commentaire")
public class Commentaire {
	
	@Id
	private String id;
	private String comment;
	private String user;
	private String ticket;
	//@DBRef
    //private User user;

    private String creationDate;

    public Commentaire(String comment) {
        this.comment = comment;
       // this.user = user;
    }
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Commentaire(String id, String comment) {
		super();
		this.id = id;
		this.comment = comment;
	}
	
	public Commentaire(String comment, String user, String ticket) {
		super();
		this.comment = comment;
		this.user = user;
		this.ticket = ticket;
	}

	public Commentaire() {
		// TODO Auto-generated constructor stub
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	
	
	

}
