package com.smpp.demo.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor 
@ToString
@Builder
@Document(collection = "Ticket")
public class Ticket {
	
	@Id
	private String id;
	private String subject, content, created_at, updated_at, completed_at;
	//@DBRef
	//private Set<User> users = new HashSet<>();
	@DBRef
	private Set<Priorities> priorities = new HashSet<>();
	private String creator;
	//@DBRef
	/*@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private Set<String> comment = new HashSet<>();*/
	
	 //@DBRef
	  //private Set<Commentaire> comment = new HashSet<>();
	
	
	
	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Ticket(String subject,String content) {
		super();
		this.subject = subject;
		this.content = content;
	}
	

	public Ticket(String created_at) {
		super();
		this.created_at = created_at;
	}


	public Ticket(String subject,String content, String created_at, String updated_at, String completed_at) {
		super();
		this.subject = subject;
		this.content = content;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.completed_at = completed_at;
	}
	
	
	public Ticket(String subject,String content, Set<Priorities> priorities) {
		super();
		this.subject = subject;
		this.content = content;
		this.priorities = priorities;
	}
	
	


	public Ticket(String subject, String content, String creator) {
		super();
		this.subject = subject;
		this.content = content;
		this.creator = creator;
	}

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getCompleted_at() {
		return completed_at;
	}
	public void setCompleted_at(String completed_at) {
		this.completed_at = completed_at;
	}
	
	public Set<Priorities> getPriorities() {
		return priorities;
	}
	public void setPriorities(Set<Priorities> priorities) {
		this.priorities = priorities;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	
	
	

}
