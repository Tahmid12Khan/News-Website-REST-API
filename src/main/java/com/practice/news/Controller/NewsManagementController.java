package com.practice.news.Controller;

import com.practice.news.Model.Utility;
import com.practice.news.Model.News;

import com.practice.news.Security.AuthenticationFacade;
import com.practice.news.Security.IAuthenticationFacade;
import com.practice.news.Service.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@Controller
public class NewsManagementController {
	private NewsService newsService;
	private IAuthenticationFacade authenticationFacade;

	public NewsManagementController(NewsService newsService, AuthenticationFacade authentication) {
		this.newsService = newsService;
		this.authenticationFacade = authentication;
	}

	@GetMapping(value = "/api/news")
	public ResponseEntity<?> showNewsList(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "1000000000") int size) {
		Page<News> news = newsService.findAllByOrderByDateDesc(Math.max(page - 1, 0), size);
		return new ResponseEntity<>(news, getStatusForNewsSize(news));
	}

	private HttpStatus getStatusForNewsSize(Page<News> news) {
		if (news.isEmpty()) {
			return HttpStatus.NO_CONTENT;
		}
		if (news.getNumberOfElements() < news.getSize()) {
			return HttpStatus.PARTIAL_CONTENT;
		}
		return HttpStatus.OK;
	}

	@GetMapping(value = "/api/news/{id}")
	public ResponseEntity<?> showNews(@PathVariable String id) {
		Optional<News> news = Optional.ofNullable(newsService.findById(id));
		return news.isPresent() ? new ResponseEntity<>(news.get(), HttpStatus.OK) :
				new ResponseEntity<>("", HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("authentication.name != 'anonymousUser'")
	@PostMapping(value = "/api/news")
	public ResponseEntity<?> addNews(@Valid @RequestBody News news, BindingResult bindingResult, Authentication authentication) {
		news.setAuthor(authenticationFacade.getAuthentication().getName());
		String message = newsService.save(news, bindingResult);
		return new ResponseEntity<>(message, Utility.getCode(message));
	}

	@PreAuthorize("authentication.name != 'anonymousUser'")
	@PutMapping(value = "/api/news")
	public ResponseEntity<?> editNews(@Valid @RequestBody News news, BindingResult bindingResult) {
		String message = newsService.update(news, authenticationFacade.getAuthentication().getName(), bindingResult);
		return new ResponseEntity<>(message, Utility.getCode(message));
	}

	@PreAuthorize("authentication.name != 'anonymousUser'")
	@DeleteMapping(value = "/api/news")
	public ResponseEntity<?> deleteNews(@RequestBody News news, BindingResult bindingResult) {
		String message = newsService.delete(news, authenticationFacade.getAuthentication().getName(), bindingResult);
		return new ResponseEntity<>(message, Utility.getCode(message));
	}

}
