package com.practice.news.Controller;

import com.practice.news.Model.User;
import com.practice.news.Security.AuthenticationFacade;
import com.practice.news.Security.IAuthenticationFacade;
import com.practice.news.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserManagementController {
	private UserService userService;

	private IAuthenticationFacade authentication;

	@Autowired
	public UserManagementController(UserService userService, AuthenticationFacade authentication) {
		this.userService = userService;
		this.authentication = authentication;

	}

	@GetMapping(value = "/api/user")
	public ResponseEntity<?> addUser() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/api/user")
	public ResponseEntity<?> findUser(@RequestBody User user) {
		System.out.println(user.toString());
		String message = userService.findByUseridAndPassword(user);
		return new ResponseEntity<>(message, userService.getCode(message));
	}

	@PostMapping(value = "/api/register")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user,
									 BindingResult bindingResult) {
		System.out.println("SS " + user.toString());
		String message = userService.save(user, bindingResult);
		return new ResponseEntity<>(message, userService.getCode(message));
	}

	@GetMapping(value = "/login")
	public String logUser(Model model) {
//		System.out.println("GET Mapping: " + authentication.getAuthentication().getName());
		if (!annonymousUser()) {
			return redirect();
		}
		model.addAttribute("user", new User());
		return "login";
	}

	private String redirect() {
		return "redirect:/";
	}

	private boolean annonymousUser() {
		return authentication.getAuthentication() instanceof AnonymousAuthenticationToken;
	}

}
