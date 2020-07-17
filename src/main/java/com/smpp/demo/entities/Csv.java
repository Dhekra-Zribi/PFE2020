package com.smpp.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Csv")
public class Csv {
	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	@Id
	private long id;
	
	private String destAddr;

	public Csv(String destAddr) {
		
		this.destAddr = destAddr;
	}

	public Csv() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Long getId() {
		return id;
	}

	public Csv(Long id, String destAddr) {
		super();
		this.id = id;
		this.destAddr = destAddr;
	}

	public String getDestAddr() {
		return destAddr;
	}

	public void setDestAddr(String destAddr) {
		this.destAddr = destAddr;
	}
	
}
