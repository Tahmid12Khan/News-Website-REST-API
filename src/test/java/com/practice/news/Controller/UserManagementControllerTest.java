package com.practice.news.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.news.Model.User;
import com.practice.news.Model.Utility;
import com.practice.news.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserManagementControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;

	private String convertToJSON(Object o) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(o);
	}

	@Test
	public void useridAndPasswordMatch() throws Exception {
		User u = new User();
		u.setUserid("Tk");
		u.setPassword("12");
		u.setId((long) 10);
		when(userService.findByUseridAndPassword(any())).thenReturn(Utility.success);
		this.mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON).content(convertToJSON(u))).
				andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void useridAndPasswordDidNotMatch() throws Exception {
		User u = new User();
		u.setUserid("Tk");
		u.setPassword("12");
		u.setId((long) 10);
		when(userService.findByUseridAndPassword(any())).thenReturn(Utility.passwordDidNotMatch);
		this.mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON).content(convertToJSON(u))).
				andDo(print()).andExpect(status().isExpectationFailed());
	}

	@Test
	public void registerWhenPasswordDidNotMatch() throws Exception {
		User u = new User();
		u.setUserid("Tk");
		u.setPassword("12");
		u.setId((long) 10);
		when(userService.save(any(), any())).thenReturn(Utility.passwordDidNotMatch);
		this.mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(convertToJSON(u))).
				andDo(print()).andExpect(status().isExpectationFailed());
	}

	@Test
	public void registerWhenSuccess() throws Exception {
		User u = new User();
		u.setUserid("Tk");
		u.setPassword("12");
		u.setId((long) 10);
		when(userService.save(any(), any())).thenReturn(Utility.successCreated);
		this.mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(convertToJSON(u))).
				andDo(print()).andExpect(status().isCreated());
	}
}
