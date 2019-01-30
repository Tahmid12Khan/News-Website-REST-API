package com.practice.news.Service;

import com.practice.news.Model.News;
import com.practice.news.Persistence.NewsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NewsServiceTest {
	@MockBean
	private NewsRepository newsRepository;
	@Autowired
	private NewsService newsService;

	@Before
	public void setup() {
		when(newsRepository.findById((long) 10)).thenReturn(Optional.ofNullable(new News()));
	}

	@Test
	public void findById() {
		when(newsRepository.findById((long) 10)).thenReturn(Optional.ofNullable(new News()));
		assertThat(newsService.findById("10")).isNotNull();
		assertThat(newsService.findById("101")).isNull();
		assertThat(newsService.findById("101a")).isNull();
	}
}
