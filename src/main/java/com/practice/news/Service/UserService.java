package com.practice.news.Service;

import com.practice.news.Model.Utility;
import com.practice.news.Model.User;
import com.practice.news.Persistence.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static com.practice.news.Model.Utility.*;

@Service
public class UserService implements UserDetailsService {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = bCryptPasswordEncoder;
	}


	private Optional<User> findByUserid(User user) {
		return userRepository.findByUserid(user.getUserid());
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public String findByUseridAndPassword(User u) {
		Optional<User> user = findByUserid(u);
		if (!user.isPresent()) {
			return USER_NOT_FOUND;
		}
		User foundUser = user.get();
		if (!passwordEncoder.matches(u.getPassword(), foundUser.getPassword())) {
			return PASSWORD_DID_NOT_MATCH;
		}
		return SUCCESS;
	}

	public String save(User user, BindingResult bindingResult) {
		user.setId((long) 0);
		String message = getResult(user, bindingResult);
		if (!message.equals(SUCCESS)) return message;
		save(user);
		return SUCCESS_CREATED;
	}

	private void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setMatchingPassword(user.getPassword());
		userRepository.saveAndFlush(user);
	}

	public boolean findByUserid(String id) {
		Optional<User> user = userRepository.findByUserid(id);
		return user.isPresent();
	}


	private String getResult(User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Utility.getErrorMessages(bindingResult);
		}
		if (findByUserid(user.getUserid())) {
			return USERNAME_EXISTS;
		}
		return SUCCESS;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> u = userRepository.findByUserid(username);
		if (!u.isPresent()) {
			throw new NullPointerException("User not found");
		}
		return new UserPrincipal(u.get());

	}

}

