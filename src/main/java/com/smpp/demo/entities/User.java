package com.smpp.demo.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor 
@ToString
@Builder
@Document(collection = "User")
public class User {

	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	@Id
	private long id;
	private String emailId, userName, password, confirmPassword;
	 
	 @DBRef
	  private Set<Role> roles = new HashSet<>();

	 
	 //@ManyToOne(mappedBy = "users", fetch=FetchType.LAZY)
	 //private Role role;
	 
	public User(long id, String emailId, String userName, String password, String confirmPassword) {
		super();
		this.id = id;
		this.emailId = emailId;
		this.userName = userName;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}
	public User(String emailId, String userName, String password, String confirmPassword) {
		super();
		this.emailId = emailId;
		this.userName = userName;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}
	
	 public User(String userName, String emailId, String password) {
		    this.userName = userName;
		    this.emailId = emailId;
		    this.password = password;
		  }
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", emailId=" + emailId + ", userName=" + userName + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + "]";
	}
	
	
}
