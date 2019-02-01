package com.practice.news.Controller;

import com.practice.news.Model.Utility;
import com.practice.news.Model.User;
import com.practice.news.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserManagementController {
	private UserService userService;

	public UserManagementController(UserService userService) {
		this.userService = userService;

	}

	@GetMapping(value = "/api/user")
	public ResponseEntity<?> addUser() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/api/user")
	public ResponseEntity<?> findUser(@RequestBody User user) {
		System.out.println("Login: " + user.toString());
		String message = userService.findByUseridAndPassword(user);
		return new ResponseEntity<>(message, Utility.getCode(message));
	}

	@PostMapping(value = "/api/register")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user,
									 BindingResult bindingResult) {
		System.out.println("Register: " + user.toString());
		String message = userService.save(user, bindingResult);
		return new ResponseEntity<>(message, Utility.getCode(message));
	}

}
