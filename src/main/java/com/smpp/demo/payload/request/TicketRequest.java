package com.smpp.demo.payload.request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.smpp.demo.entities.Priorities;


public class TicketRequest {

	private String id, creator,subject, content, created_at, 
		updated_at, completed_at,newComment;
	//private Set<String> users = new HashSet<>();
	private Set<String> priorities = new HashSet<>();
	private Set<Priorities> prioritie = new HashSet<>();
	//private List<String> comment = new ArrayList<>();
	private List<String> comment;

	
	
	
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
	public Set<String> getPriorities() {
		return priorities;
	}
	public void setPriorities(Set<String> priorities) {
		this.priorities = priorities;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public List<String> getComment() {
		return comment;
	}
	public void setComment(List<String> comment) {
		this.comment = comment;
	}
	public String getNewComment() {
		return newComment;
	}
	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}
	public Set<Priorities> getPrioritie() {
		return prioritie;
	}
	public void setPrioritie(Set<Priorities> prioritie) {
		this.prioritie = prioritie;
	}
	
	
	
	
}
