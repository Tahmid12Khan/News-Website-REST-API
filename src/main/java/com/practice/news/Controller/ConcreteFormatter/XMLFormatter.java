package com.practice.news.Controller.ConcreteFormatter;

import com.fasterxml.jackson.xml.XmlMapper;
import com.practice.news.Controller.InterfaceFormatter.iFormatter;
import com.practice.news.Model.News;

import java.io.IOException;


public class XMLFormatter implements iFormatter {

	private XmlMapper xmlMapper;

	public XMLFormatter(XmlMapper xmlMapper) {
		this.xmlMapper = xmlMapper;
	}

	@Override
	public <T extends News> String stringFormat(T news) {
		System.out.println(news);
		try {
			return xmlMapper.writeValueAsString(news);
		} catch (IOException e) {
			e.printStackTrace();
			return "Error in XML";
		}
	}
}