package com.spring.cloudamqp;

import java.io.Serializable;

public class SimpleMessageDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1571576324465272666L;

	private String name;
	
	private String description;
	
	public SimpleMessageDto()  {		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "SimpleMessage [name=" + name + ", description=" + description + "]";
	}
	
	

}
