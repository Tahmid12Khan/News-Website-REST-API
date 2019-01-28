package com.practice.news.Persistence;

import com.practice.news.Model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

	Optional<List<News>> findAllByOrderByIdDesc();

	Page<News> findAllByOrderByDateDesc(Pageable pageable);

	Page<News> findAll(Pageable pageable);
	Optional<News> findById(Long id);

	Optional<List<News>> findAllByOrderByDateDesc();
}
