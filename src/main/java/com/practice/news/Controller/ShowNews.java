package com.practice.news.Controller;

//import com.practice.news.Controller.Factory.FormatterFactory;
//import com.practice.news.Controller.InterfaceFormatter.iFormatter;
//import com.practice.news.Model.News;
//import com.practice.news.Service.NewsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.Map;

@Controller
public class ShowNews {

	//private NewsService newsService;

//	@Autowired
//	public ShowNews(NewsService newsService) {
//		this.newsService = newsService;
//	}
//
//	@GetMapping(value = "/news/{id}")
//	public String showNews(@PathVariable String id, Model model) {
//		News news = newsService.findById(id);
//		model.addAttribute("news", news);
//		return "details";
//	}
//
//	@GetMapping(value = "/news/download/{option}/{id}")
//	public ResponseEntity<Resource> formatView(@PathVariable Map<String, String> req) {
//		News news = newsService.findById(req.get("id"));
//
//		System.out.println(req.get("option"));
//		iFormatter formatter = FormatterFactory.getFormatter(req.get("option"));
//		String s = formatter.stringFormat(news);
//
//		return download(s, Long.parseLong(req.get("id")));
//
//	}
//
//	private ResponseEntity<Resource> download(String s, Long id) {
//
//
//		return ResponseEntity.ok()
//				.contentType(MediaType.parseMediaType("application/octet-stream"))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + id + ".txt")
//				.body(new ByteArrayResource(s.getBytes()));
//	}


}

