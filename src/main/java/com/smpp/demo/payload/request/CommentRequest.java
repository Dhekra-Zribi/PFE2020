package com.smpp.demo.payload.request;


import org.springframework.beans.BeanUtils;

import com.smpp.demo.entities.Commentaire;
import com.smpp.demo.entities.User;

public class CommentRequest {
	
	private String id, comment, creationDate;
    private UserDto user;
    
    public static CommentRequest convertToDTO(final Commentaire comment) {
    	CommentRequest commentDTO = new CommentRequest();
        commentDTO.setUser(new UserDto());
        BeanUtils.copyProperties(comment, commentDTO);
        BeanUtils.copyProperties(comment.getUser(), commentDTO.getUser());
        return commentDTO;
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
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
    
    

}
