package com.practice.news.Model;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Map;
import java.util.TreeMap;

public class Utility {
	public final static String success = "success";
	public final static String successCreated = "created";
	public final static String successUpdated = "updated";
	public final static String successDeleted = "deleted";
	public final static String newsDoesNotExist = "News does not exist";
	public final static String unAuthorizedAccess = "You don't have access";
	public final static String usernameExists = "Username exits";
	public final static String userNotFound = "User Not Found";
	public final static String passwordDidNotMatch = "Password did not match";

	private static Map<String, HttpStatus> getStatus;

	private Utility() {

	}

	private static void init() {
		if (getStatus != null) return;
		getStatus = new TreeMap<>();
		getStatus.put(success, HttpStatus.OK);
		getStatus.put(successCreated, HttpStatus.CREATED);
		getStatus.put(successUpdated, HttpStatus.ACCEPTED);
		getStatus.put(successDeleted, HttpStatus.ACCEPTED);
		getStatus.put(newsDoesNotExist, HttpStatus.NOT_FOUND);
		getStatus.put(unAuthorizedAccess, HttpStatus.UNAUTHORIZED);
		getStatus.put(usernameExists, HttpStatus.CONFLICT);
		getStatus.put(userNotFound, HttpStatus.NOT_FOUND);
		getStatus.put(passwordDidNotMatch, HttpStatus.EXPECTATION_FAILED);
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
