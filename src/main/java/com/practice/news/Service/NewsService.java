package com.practice.news.Service;

import com.practice.news.Model.Utility;
import com.practice.news.Model.News;
import com.practice.news.Persistence.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


import java.util.Optional;

@Service
public class NewsService {

	private NewsRepository newsRepository;

	@Autowired
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
		return Utility.successCreated;


	}

	public String update(News news, String username, BindingResult bindingResult) {

		String message = getResult(news, username, bindingResult);
		if (!message.equals(Utility.success)) return message;
		news.setAuthor(username);
		newsRepository.saveAndFlush(news);
		return Utility.successUpdated;

	}

	private String getResult(News news, String username, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Utility.getErrorMessages(bindingResult);

		}
		if (!isNewsExists(news)) {
			return Utility.newsDoesNotExist;

		}
		String author = findById(news.getId()).getAuthor();
		if (!username.equals(author)) {
			return Utility.unAuthorizedAccess;
		}

		return Utility.success;
	}

	public String delete(News news, String username, BindingResult bindingResult) {
		String message = getResult(news, username, bindingResult);
		if (!message.equals(Utility.success)) return message;
		newsRepository.delete(news);
		return Utility.successDeleted;
	}


}
