package com.practice.news.Service;

import com.practice.news.Model.Utility;
import com.practice.news.Model.News;
import com.practice.news.Persistence.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


import java.util.Optional;

import static com.practice.news.Model.Utility.*;

@Service
public class NewsService {

	private NewsRepository newsRepository;

	public NewsService(NewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}

	private boolean isNewsExists(News news) {
		try {
			return newsRepository.existsById(news.getId());
		} catch (Exception e) {
			return false;
		}

	}

	public Page<News> findAllByOrderByDateDesc(int page, int size) {
		return newsRepository.findAll(PageRequest.of(page, size, Sort.by("date").descending()));
	}

	private News findById(Long id) {
		Optional<News> news = newsRepository.findById(id);
		return news.get();
	}

	public News findById(String id) {
		try {
			return findById(Long.parseLong(id));
		} catch (Exception e) {
			return null;
		}
	}

	public String save(News news, BindingResult bindingResult) {
		news.setId((long) 0);
		if (bindingResult.hasErrors()) {
			return Utility.getErrorMessages(bindingResult);
		}
		newsRepository.saveAndFlush(news);
		return SUCCESS_CREATED;
	}

	public String update(News news, String username, BindingResult bindingResult) {
		String message = getResult(news, username, bindingResult);
		if (!message.equals(SUCCESS)) return message;
		news.setAuthor(username);
		newsRepository.saveAndFlush(news);
		return SUCCESS_UPDATED;
	}

	private String getResult(News news, String username, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return getErrorMessages(bindingResult);
		}
		if (!isNewsExists(news)) {
			return NEWS_DOES_NOT_EXIST;
		}
		String author = findById(news.getId()).getAuthor();
		if (!username.equals(author)) {
			return UNAUTHORIZED_ACCESS;
		}

		return SUCCESS;
	}

	public String delete(News news, String username, BindingResult bindingResult) {
		String message = getResult(news, username, bindingResult);
		if (!message.equals(SUCCESS)) return message;
		newsRepository.delete(news);
		return SUCCESS_DELETED;
	}
}
