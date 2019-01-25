package com.practice.news.Service;


import com.practice.news.Error.Invalid;
import com.practice.news.Model.User;
import com.practice.news.Persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private final String usernameExists = "Username exits";
	private final String sucessCreated = "created";
	private final String sucess = "success";
	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

		this.userRepository = userRepository;
		this.passwordEncoder = bCryptPasswordEncoder;
	}

//	public boolean findByUseridAndAndPassword(String userid, String password) {
////		return userRepository.findByUseridAndAndPassword(userid, password).isPresent();
////
////	}

	private Optional<User> findByUserid(User user) {
		return userRepository.findByUserid(user.getUserid());

	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public boolean findByUseridAndPassword(User u, BindingResult bindingResult) {
		Optional <User> user = findByUserid(u);
		if(!user.isPresent()){
			bindingResult.addError(new ObjectError(bindingResult.getObjectName(), "Invalid username"));
			return false;
		}
		User foundUser = user.get();
		if(!passwordEncoder.matches(u.getPassword(), foundUser.getPassword())){
			bindingResult.addError(new ObjectError(bindingResult.getObjectName(), "Invalid username or password"));
			return false;
		}
		return true;
	}

	public String save(User user, BindingResult bindingResult) {
		user.setId((long) 0);
		String message = getResult(user, bindingResult);
		if (!message.equals(sucess)) return message;
		save(user);
		return sucessCreated;
	}

	private void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		System.out.println(user.getPassword());
		user.setMatchingPassword(user.getPassword());
		userRepository.saveAndFlush(user);
	}

	public boolean findByUserid(String id) {
		Optional<User> user = userRepository.findByUserid(id);
		System.out.println("Present " + user.isPresent());
		return user.isPresent();
	}


	private String getResult(User user, BindingResult bindingResult) {
		String errMessage;
		if (bindingResult.hasErrors()) {
			return getErrorMessages(bindingResult);
		}
		if (findByUserid(user.getUserid())) {
			return usernameExists;
		}
		return sucess;
	}

	private String getErrorMessages(BindingResult bindingResult) {
		StringBuilder errMsg = new StringBuilder();
		if (bindingResult.hasErrors()) {
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errMsg.append(fieldError.getDefaultMessage() + "\n");
			}
		}
		return errMsg.toString();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional <User> u = userRepository.findByUserid(username);
		if(!u.isPresent()){
			throw new Invalid("User is not present");
		}
		return new UserPrincipal(u.get());

	}

	public HttpStatus getCode(String message) {
		if (message.equals(sucessCreated)) return HttpStatus.CREATED;
//		else if (message.equals(newsDoesNotExist)) return HttpStatus.NOT_FOUND;
//		else if (message.equals(unAuthorizedAccess)) return HttpStatus.FORBIDDEN;
		return HttpStatus.METHOD_NOT_ALLOWED;
	}
}

