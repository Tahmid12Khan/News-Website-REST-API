package com.practice.news.Controller;


import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.practice.news.Model.News;
import com.practice.news.Service.NewsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NewsManagementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NewsService newsService;

	@InjectMocks
	public NewsManagementController newsManagementController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(newsManagementController)
				.build();
	}

	private Page<News> getNews(int pageNo, int size) {
		List<News> news = Arrays.asList(new News(), new News(), new News(), new News());
		Page<News> page = new PageImpl<>(news, PageRequest.of(pageNo, size), news.size());
		return page;
	}

	@Test
	public void whenAskForAllNewsReturnOK() throws Exception {
		when(newsService.findAllByOrderByDateDesc(anyInt(), anyInt())).thenReturn(getNews(0, 4));
		this.mockMvc.perform(get("/api/news")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void whenAskForAllNewsReturnPartialContent() throws Exception {
		when(newsService.findAllByOrderByDateDesc(anyInt(), anyInt())).thenReturn(getNews(0, 1000));
		this.mockMvc.perform(get("/api/news?page=0&&size=1000")).andDo(print()).andExpect(status().isPartialContent());
	}

	@Test
	public void whenAskForNewsByIDReturnFound() throws Exception {
		when(newsService.findById("2")).thenReturn(new News());
		this.mockMvc.perform(get("/api/news/2")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void whenAskForNewsByIDReturnNotFound() throws Exception {
		this.mockMvc.perform(get("/api/news/3")).andDo(print()).andExpect(status().isNotFound());
	}


}