package com.practice.news.Service;

import com.practice.news.Model.Utility;
import com.practice.news.Model.User;
import com.practice.news.Persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
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
			return Utility.userNotFound;
		}
		User foundUser = user.get();
		if (!passwordEncoder.matches(u.getPassword(), foundUser.getPassword())) {
			return Utility.passwordDidNotMatch;
		}
		return Utility.success;
	}

	public String save(User user, BindingResult bindingResult) {
		user.setId((long) 0);
		String message = getResult(user, bindingResult);
		if (!message.equals(Utility.success)) return message;
		save(user);
		return Utility.successCreated;
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
			return Utility.usernameExists;
		}
		return Utility.success;
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

