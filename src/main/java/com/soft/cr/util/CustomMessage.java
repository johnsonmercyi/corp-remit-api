package com.soft.cr.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomMessage {

    private final boolean isError;
	private final String name;
	private final String message;
	private final String subMessage;

	public CustomMessage(@JsonProperty boolean isError, 
			@JsonProperty String name, 
			@JsonProperty String message,
			@JsonProperty String subMessage) {
		this.isError = isError;
		this.name = name;
		this.message = message;
		this.subMessage = subMessage;
	}
	
	public boolean isError() {
		return isError;
	}

	public String getName() {
		return name;
	}

	public String getMessage () {
		return this.message;
	}
	
	public String getSubMessage () {
		return this.subMessage;
	}
	
	@Override
	public String toString() {
		return this.message;
	}
    
}
