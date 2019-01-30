package com.practice.news.Model;

import com.practice.news.Validation.FieldMatch;

import javax.persistence.*;

import javax.validation.constraints.Size;


@Entity
@FieldMatch(first = "password", second = "matchingPassword", message = "Password did not match")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Size(min = 1, message = "Name cannot be of size 0")
	@Size(max = 50, message = "Name cannot be more than 50 characters")
	private String name;
	@Column(unique = true)
	@Size(min = 1, message = "Username cannot be of size 0")
	@Size(max = 20, message = "Username cannot be more than 20 characters")
	private String userid;
	@Size(min = 1, message = "Password cannot be of size 0")
	private String password;
	private String matchingPassword;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", userid='" + userid + '\'' +
				", password='" + password + '\'' +
				", matchingPassword='" + matchingPassword + '\'' +
				'}';
	}

	public void setName(String name) {
		this.name = name;
	}
}
