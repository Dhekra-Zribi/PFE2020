package com.smpp.demo.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor 
@ToString
@Builder
@Document(collection = "Sms")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sms  {
	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	@Id
	private long id;
	private  String shortMessage;
	private String sourceAddr;
	private String destAddr;
	private String dateTime;
	private String time;
	private String type;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getShortMessage() {
		return shortMessage;
	}
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}
	public String getSourceAddr() {
		return sourceAddr;
	}
	public void setSourceAddr(String sourceAddr) {
		this.sourceAddr = sourceAddr;
	}
	public String getDestAddr() {
		return destAddr;
	}
	public void setDestAddr(String destAddr) {
		this.destAddr = destAddr;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Sms() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Sms(long id, String shortMessage, String sourceAddr, String destAddr, String dateTime, String time, String type) {
		super();
		this.id = id;
		this.shortMessage = shortMessage;
		this.sourceAddr = sourceAddr;
		this.destAddr = destAddr;
		this.dateTime = dateTime;
		this.time = time;
		this.type = type;
	}
	
	public Sms(long id, String shortMessage, String sourceAddr,String destAddr) {
		this.id = id;
		this.shortMessage = shortMessage;
		this.sourceAddr = sourceAddr;
		this.destAddr=destAddr;
	}
	@Override
	public String toString() {
		return "Sms [id=" + id + ", shortMessage=" + shortMessage + ", sourceAddr=" + sourceAddr + ", destAddr="
				+ destAddr + ", dateTime=" + dateTime + ", time=" + time + ", type=" + type + "]";
	}
	
	
	
	
	
	
}
