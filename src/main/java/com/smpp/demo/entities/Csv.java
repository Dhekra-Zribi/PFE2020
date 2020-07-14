package com.smpp.demo.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Csv")
public class Csv {
	
	private String destAddr;

	public Csv(String destAddr) {
		
		this.destAddr = destAddr;
	}

	public Csv() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDestAddr() {
		return destAddr;
	}

	public void setDestAddr(String destAddr) {
		this.destAddr = destAddr;
	}
	
}
