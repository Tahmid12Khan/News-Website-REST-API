package com.practice.news.Controller;


import com.practice.news.Model.News;

import com.practice.news.Model.PagerModel;
import com.practice.news.Service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

	private NewsService newsService;

	@Autowired
	public HomeController(NewsService newsService) {
		this.newsService = newsService;
	}

	@GetMapping("/")
	public String greeting(@RequestParam("page") Optional<Integer> page, Model model, HttpSession session) {
//		List<News> news = newsService.findAllByOrderByIdDesc();
//		model.addAttribute("news", news);
//		int evalPage = (page.orElse(0) < 1) ? 0 : page.get() - 1;
//
//		Page<News> newsList = newsService.findAllByOrderByDateDesc(PageRequest.of(evalPage, 4));
//		PagerModel pager = new PagerModel(newsList.getTotalPages(), newsList.getNumber());
//		System.out.println(newsList.getNumber() + " st " + pager.getStartPage() + " en " + pager.getEndPage());
//		model.addAttribute("newsList", newsList);
//		model.addAttribute("selectedPageSize", 4);
//		model.addAttribute("pageSizes", 4);
//		model.addAttribute("pager", pager);

		return "homepage";
	}

}
