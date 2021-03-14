package org.springframework.samples.petclinic.model;

import com.sun.istack.NotNull;

import java.io.Serializable;

public class WebPage implements Serializable {

	private String htmlPage;

	public WebPage(String page) {
		this.htmlPage = page;
	}

	public String getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
	}

	@Override
	public String toString() {
		return this.htmlPage.toString();
	}

}
