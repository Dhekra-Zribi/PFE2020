package com.smpp.demo.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ConfigSmsc")
public class ConfigSmsc {

	@Id
	private String id;
	private String ipAddress, systemId, password ;
	private int port;
	
	public ConfigSmsc(String ipAddress, String systemId, String password, int port) {
		super();
		this.ipAddress = ipAddress;
		this.systemId = systemId;
		this.password = password;
		this.port = port;
	}
	
	public ConfigSmsc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
