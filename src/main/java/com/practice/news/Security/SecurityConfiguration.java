package com.practice.news.Security;


import com.practice.news.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private UserService userDetailsService;

	private DataSource dataSource;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public SecurityConfiguration(UserService userDetailsService, DataSource dataSource,
								 PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.dataSource = dataSource;
		this.passwordEncoder = passwordEncoder;

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/user/**").permitAll()
				.antMatchers("/api/news").permitAll()
				.antMatchers("/api/news/**").permitAll()
				.antMatchers("/api/register").permitAll()
				.anyRequest().denyAll()
				.and().httpBasic()
		;
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder)
				.and()
				.authenticationProvider(authenticationProvider())
				.jdbcAuthentication()
				.dataSource(dataSource)
		;
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
				.antMatchers("/resources/**", "/js/**", "/css/**", "/import-headers.html");
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}

}
