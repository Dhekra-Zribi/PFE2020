package com.smpp.demo.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

public class SignupRequest {
  @NotBlank
    @Size(min = 3, max = 20)
    private String userName;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String emailId;
    
    private Set<String> roles;
    
    
    private String mobile;
    
    private String phone;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getEmailId() {
        return emailId;
    }
 
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRoles() {
      return this.roles;
    }
    
    public void setRoles(Set<String> roles) {
      this.roles = roles;
    }

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
    

}
