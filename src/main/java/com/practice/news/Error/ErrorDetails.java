package com.practice.news.Error;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ResponseStatus
public class ErrorDetails {
	private Date timestamp;
	private List<String> message;
	private List<String> details;

	public Date getTimestamp() {
		return timestamp;
	}

	public List<String> getMessage() {
		return message;
	}

	public List<String> getDetails() {
		return details;
	}

	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = Arrays.asList(message);
		this.details = Arrays.asList(details);
	}
}