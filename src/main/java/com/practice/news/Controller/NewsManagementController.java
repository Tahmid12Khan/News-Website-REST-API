package com.practice.news.Controller;

import com.practice.news.Model.News;

import com.practice.news.Security.AuthenticationFacade;
import com.practice.news.Security.IAuthenticationFacade;
import com.practice.news.Service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class NewsManagementController {
	//public static final Logger logger = (Logger) LoggerFactory.getLogger(NewsManagementController.class);
	private NewsService newsService;
	private IAuthenticationFacade authentication;

	@Autowired
	public NewsManagementController(NewsService newsService, AuthenticationFacade authentication) {
		this.newsService = newsService;
		this.authentication = authentication;
	}

	@GetMapping(value = "/api/news")
	public ResponseEntity<?> showNewsList() {
		List<News> news = newsService.findAllByOrderByDateDesc();
		return new ResponseEntity<>(news, HttpStatus.OK);
	}

	@GetMapping(value = "/api/news/{id}")
	public ResponseEntity<?> showNews(@PathVariable String id) {
		News news = newsService.findById(id);
		return new ResponseEntity<>(news, HttpStatus.OK);
	}

	@PostMapping(value = "/api/news")
	public ResponseEntity<?> addNews(@Valid @RequestBody News news, BindingResult bindingResult) {
		System.out.println(news.toString());
		String message = newsService.save(news, bindingResult);
		System.out.println("Post " + authentication.getAuthentication().getName());
		return new ResponseEntity<>(message, newsService.getCode(message));
	}

//	@GetMapping(value = "/edit-news/{id}")
//	public String editNews(@PathVariable String id, Model model) {
//		model.addAttribute("news", newsService.findById(id));
//		return "edit-news";
//	}

	@PutMapping(value = "/api/news")
	public ResponseEntity<?> editNews(@Valid @RequestBody News news, BindingResult bindingResult) {
		System.out.println("Put " + news.toString());
		String message = newsService.update(news, authentication.getAuthentication().getName(), bindingResult);
		return new ResponseEntity<>(message, newsService.getCode(message));
	}

	@DeleteMapping(value = "/api/news")
	public ResponseEntity<?> deleteNews(@Valid @RequestBody News news, BindingResult bindingResult) {
		String message = newsService.delete(news, authentication.getAuthentication().getName(), bindingResult);
		return new ResponseEntity<>(message, newsService.getCode(message));
	}


}
