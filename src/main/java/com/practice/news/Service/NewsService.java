package com.practice.news.Service;

import com.practice.news.Error.Invalid;
import com.practice.news.Model.News;
import com.practice.news.Persistence.NewsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
		return newsRepository.existsById(news.getId());
	}

	public List<News> findAllByOrderByIdDesc() {
		Optional<List<News>> news = newsRepository.findAllByOrderByIdDesc();
		return news.isPresent() ? news.get() : Arrays.asList(
				new News("No news available", "-",
						"-", new Date()));

	}

	public Page<News> findAllByOrderByDateDesc(Pageable pageable) {
		Optional<Page<News>> news = newsRepository.findAllByOrderByDateDesc(pageable);
		return news.get();

	}

	public List<News> findAllByOrderByDateDesc() {
		Optional<List<News>> news = newsRepository.findAllByOrderByDateDesc();
		return news.get();

	}


	private News findById(Long id) {
		Optional<News> news = newsRepository.findById(id);
		news.orElseThrow(() -> new Invalid("Id not found"));
		System.out.println("News got " + news.get().getId());
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
		if (message.equals(sucessUpdated)) return HttpStatus.ACCEPTED;
		if (message.equals(sucessDeleted)) return HttpStatus.ACCEPTED;
		else if (message.equals(newsDoesNotExist)) return HttpStatus.NOT_FOUND;
		else if (message.equals(unAuthorizedAccess)) return HttpStatus.FORBIDDEN;
		return HttpStatus.METHOD_NOT_ALLOWED;
	}
}
