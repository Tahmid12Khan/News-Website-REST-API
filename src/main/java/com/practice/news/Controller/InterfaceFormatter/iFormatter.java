package com.practice.news.Controller.InterfaceFormatter;

import com.practice.news.Model.News;

public interface iFormatter {
	<T extends News> String stringFormat(T news);
}
