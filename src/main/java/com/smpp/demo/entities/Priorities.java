package com.smpp.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Priorities")
public class Priorities {

	@Id
    private String priorities_id;

    private Epriorities name;

	public String getPriorities_id() {
		return priorities_id;
	}

	public void setPriorities_id(String priorities_id) {
		this.priorities_id = priorities_id;
	}

	public Epriorities getName() {
		return name;
	}

	public void setName(Epriorities name) {
		this.name = name;
	}
    
    
}
