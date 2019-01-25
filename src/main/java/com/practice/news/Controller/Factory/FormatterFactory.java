package com.practice.news.Controller.Factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.xml.XmlMapper;
import com.practice.news.Controller.ConcreteFormatter.JSONFormatter;
import com.practice.news.Controller.ConcreteFormatter.XMLFormatter;
import com.practice.news.Controller.InterfaceFormatter.iFormatter;

public class FormatterFactory {
	private FormatterFactory() {

	}

	public static iFormatter getFormatter(String choice) {
		if (choice.equals("json")) {
			return new JSONFormatter(new ObjectMapper());
		}
		if (choice.equals("xml")) {
			return new XMLFormatter(new XmlMapper());
		}

		return null;
	}
}
