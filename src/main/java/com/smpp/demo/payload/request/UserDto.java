package com.smpp.demo.payload.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.smpp.demo.entities.Role;

public class UserDto {
	
	private long id;
	private String userName;

}
