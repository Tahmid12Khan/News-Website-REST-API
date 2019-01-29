package com.practice.news.Service;

import com.practice.news.Model.News;
import com.practice.news.Persistence.NewsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

@Service
public class NewsService {

	private NewsRepository newsRepository;
	private final String success = "sucess";
	private final String sucessCreated = "created";
	private final String sucessUpdated = "updated";
	private final String sucessDeleted = "deleted";
	private final String newsDoesNotExist = "News does not exist";
	private final String unAuthorizedAccess = "You don't have access";

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
			return getErrorMessages(bindingResult);
		}
		newsRepository.saveAndFlush(news);
		return sucessCreated;


	}

	public String update(News news, String username, BindingResult bindingResult) {

		String message = getResult(news, username, bindingResult);
		if (!message.equals(success)) return message;
		news.setAuthor(username);
		newsRepository.saveAndFlush(news);
		return sucessUpdated;

	}

	private String getResult(News news, String username, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return getErrorMessages(bindingResult);

		}
		if (!isNewsExists(news)) {
			return newsDoesNotExist;

		}
		String author = findById(news.getId()).getAuthor();
		if (!username.equals(author)) {
			return unAuthorizedAccess;
		}

		return success;
	}

	public String delete(News news, String username, BindingResult bindingResult) {
		String message = getResult(news, username, bindingResult);
		if (!message.equals(success)) return message;
		newsRepository.delete(news);
		return sucessDeleted;
	}

	private String getErrorMessages(BindingResult bindingResult) {
		StringBuilder errMsg = new StringBuilder();
		if (bindingResult.hasErrors()) {
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errMsg.append(fieldError.getDefaultMessage() + "\n");
			}
		}
		return errMsg.toString();
	}

	public HttpStatus getCode(String message) {
		if (message.equals(sucessCreated)) return HttpStatus.CREATED;
		else if (message.equals(sucessUpdated)) return HttpStatus.ACCEPTED;
		else if (message.equals(sucessDeleted)) return HttpStatus.ACCEPTED;
		else if (message.equals(newsDoesNotExist)) return HttpStatus.NOT_FOUND;
		else if (message.equals(unAuthorizedAccess)) return HttpStatus.FORBIDDEN;
		return HttpStatus.METHOD_NOT_ALLOWED;
	}
}
