package com.practice.news.Persistence;

import com.practice.news.Model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

	Page<News> findAll(Pageable pageable);

}
