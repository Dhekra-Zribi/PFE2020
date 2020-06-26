package com.smpp.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor 
@ToString
@Builder
@Document(collection = "CountMsgDate")
public class CountMsgDate {
	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	@Id
	private long id;
	private String date;
	private long nb;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getNb() {
		return nb;
	}
	public void setNb(long nb) {
		this.nb = nb;
	}
	public CountMsgDate(long id, String date, long nb) {
		super();
		this.id = id;
		this.date = date;
		this.nb = nb;
	}
	public CountMsgDate() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CountMsgDate [id=" + id + ", date=" + date + ", nb=" + nb + "]";
	}
	

}
