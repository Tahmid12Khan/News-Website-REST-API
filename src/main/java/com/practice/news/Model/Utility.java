package com.practice.news.Model;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Map;
import java.util.TreeMap;

public class Utility {
	public final static String SUCCESS = "success";
	public final static String SUCCESS_CREATED = "created";
	public final static String SUCCESS_UPDATED = "updated";
	public final static String SUCCESS_DELETED = "deleted";
	public final static String NEWS_DOES_NOT_EXIST = "News does not exist";
	public final static String UNAUTHORIZED_ACCESS = "You don't have access";
	public final static String USERNAME_EXISTS = "Username exits";
	public final static String USER_NOT_FOUND = "User Not Found";
	public final static String PASSWORD_DID_NOT_MATCH = "Password did not match";

	private static Map<String, HttpStatus> getStatus;

	private Utility() {

	}

	private static void init() {
		if (getStatus != null) return;
		getStatus = new TreeMap<>();
		getStatus.put(SUCCESS, HttpStatus.OK);
		getStatus.put(SUCCESS_CREATED, HttpStatus.CREATED);
		getStatus.put(SUCCESS_UPDATED, HttpStatus.ACCEPTED);
		getStatus.put(SUCCESS_DELETED, HttpStatus.ACCEPTED);
		getStatus.put(NEWS_DOES_NOT_EXIST, HttpStatus.NOT_FOUND);
		getStatus.put(UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
		getStatus.put(USERNAME_EXISTS, HttpStatus.CONFLICT);
		getStatus.put(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
		getStatus.put(PASSWORD_DID_NOT_MATCH, HttpStatus.EXPECTATION_FAILED);
	}

	public static HttpStatus getCode(String message) {
		init();
		return getStatus.get(message) != null ? getStatus.get(message) : HttpStatus.BAD_REQUEST;
	}

	public static String getErrorMessages(BindingResult bindingResult) {
		StringBuilder errMsg = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errMsg.append(fieldError.getDefaultMessage()).append("\n");
		}
		for (ObjectError objectError : bindingResult.getGlobalErrors()) {
			errMsg.append(objectError.getDefaultMessage()).append("\n");
		}

		return errMsg.toString();
	}

}
