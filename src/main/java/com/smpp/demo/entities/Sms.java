package com.smpp.demo.entities;



import org.smpp.pdu.Address;
import org.smpp.pdu.ShortMessage;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "Sms")
@AllArgsConstructor
@NoArgsConstructor @ToString

@Builder


public class Sms  {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShortMessage() {
		return shortMessage;
	}
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}
	public int getSourceAddr() {
		return sourceAddr;
	}
	public void setSourceAddr(int sourceAddr) {
		this.sourceAddr = sourceAddr;
	}
	public int getDestAddr() {
		return destAddr;
	}
	public void setDestAddr(int destAddr) {
		this.destAddr = destAddr;
	}
	@Id
	private String id;
	private  String shortMessage;
	private int sourceAddr;
	private int destAddr;
	
	
	
}
