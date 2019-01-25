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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
	public ResponseEntity<?> addUser(Model model) {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/api/register")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user,
									 BindingResult bindingResult) {
		String message = userService.save(user, bindingResult);
		return new ResponseEntity<>(message, userService.getCode(message));
	}

	@GetMapping(value = "/login")
	public String logUser(Model model) {
//		System.out.println("GET Mapping: " + authentication.getAuthentication().getName());
		if(!annonymousUser() ){
			return redirect();
		}
		model.addAttribute("user", new User());
		return "login";
	}
//
//	@PostMapping(value = "/login")
//	public String verifyUser(@ModelAttribute User user, BindingResult bindingResult, HttpSession session) {
//		//System.out.println("Name: " + userid);
//	//	System.out.println(authentication.getAuthentication().getName());
//		if (!userService.findByUseridAndPassword(user, bindingResult)) {
//			System.out.println("Username: " + user.getUserid() + ", " + "password: " + user.getPassword());
//
//			return "login";
//		}
//		System.out.println("Success " + authentication.getAuthentication().getName());
//		session.setAttribute("userid", user.getUserid());
//		//userService.save(user);
//		return "redirect:/?s=login+success";
//	}

//	@GetMapping(value = "/logout")
//	public String logout(Model model, HttpSession httpSession) {
//		if (!loggedIn(httpSession)) throwException();
//		httpSession.invalidate();
//		model.addAttribute("logout-msg", "You have been successfully logged out");
//		return "redirect:/";
//	}

	private String redirect(){
		return "redirect:/";
	}
	private boolean annonymousUser(){
		return authentication.getAuthentication() instanceof AnonymousAuthenticationToken;
	}

}
