package com.practice.news;

import com.practice.news.Controller.NewsManagementController;
import com.practice.news.Controller.HomeController;
import com.practice.news.Controller.ShowNews;
import com.practice.news.Persistence.NewsRepository;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsApplicationTests {
	@Autowired
	private NewsManagementController newsManagementController;
	@Autowired
	private HomeController homeController;
	@Autowired
	private ShowNews showNews;

	@Test
	public void contextLoads() {
	}


	@Autowired
	NewsRepository newsRepository;

	@Test
	public void testControllers() throws Exception {

		assertThat(newsManagementController).isNotNull();
		assertThat(homeController).isNotNull();
		assertThat(showNews).isNotNull();

	}


}

