package org.springframework.samples.petclinic.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.springframework.samples.petclinic.model.WebPage;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.samples.petclinic.utility.StringSimilarity.similarity;


public class CatchPageWebDriverEventListener extends AbstractWebDriverEventListener {

	private List<WebPage> htmlPages;
	private WebPage currentPage;

	public CatchPageWebDriverEventListener(){
		this.htmlPages=new ArrayList<>();
		this.currentPage=new WebPage("");
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		htmlPages.add(new WebPage(driver.getPageSource()));
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		before(driver);
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		after(driver);
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		before(driver);
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		after(driver);
	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		before(driver);
	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		after(driver);
	}

	private void before(WebDriver driver){
		currentPage=new WebPage(driver.getPageSource());
	}
	private void after(WebDriver driver){
		String after=driver.getPageSource();
		if(similarity(currentPage.getHtmlPage(),after)<StringSimilarity.MAX_DIFFERENCE)
			htmlPages.add(new WebPage(after));
	}
	public List<WebPage> getHtmlPages() {
		return htmlPages;
	}

}
