package org.springframework.samples.petclinic.model;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.samples.petclinic.utility.ActionWrapper;
import org.springframework.samples.petclinic.utility.ActionsWrapper;
import org.springframework.samples.petclinic.utility.CatchPageWebDriverEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.not;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TestRunner {


	public TestRunner(){
	}

	public List<WebPage> testRunner(TestCase test){
		//Creazione driver
		WebDriver driver = new ChromeDriver();
		EventFiringWebDriver driverEvent = new EventFiringWebDriver(driver);
		CatchPageWebDriverEventListener listener = new CatchPageWebDriverEventListener();
		driver = driverEvent.register(listener);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		Map<String, Object> vars;
		JavascriptExecutor js;
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();


		for (Command currCommand:test.getCommands()) {
			String azione= currCommand.getCommand();
			//elenco azioni (aiutatemi)
			if(azione.equals("open"))driver.get(test.getUrl());
			else if(azione.equals("setWindowSize")){
				String[] size=currCommand.getTarget().split("x",2);
				driver.manage().window().setSize(new Dimension(Integer.parseInt(size[0]),Integer.parseInt(size[1])));
			}
			else if(azione.equals("click")){
				WebElement element=identifyElement(currCommand,driver);
				js.executeScript("arguments[0].click();", element);
			}
			else if(azione.equals("type")){
				WebElement element=identifyElement(currCommand,driver);
				element.clear();
				element.sendKeys(currCommand.getValue());
			}
			else if(azione.equals("sendKeys")){
				WebElement element=identifyElement(currCommand,driver);
				String[] specialCharacter=currCommand.getValue().split("_",2);
				specialCharacter[1] = specialCharacter[1].substring(0, specialCharacter[1].length() - 1);
				switch (Objects.requireNonNull(specialCharacter[1])){
					case "ENTER": element.sendKeys(Keys.ENTER);
					break;
					case "LEFT":element.sendKeys(Keys.ARROW_LEFT);
					break;
					case "RIGHT":element.sendKeys(Keys.ARROW_RIGHT);
					break;
					case "UP":element.sendKeys(Keys.ARROW_UP);
					break;
					case "DOWN":element.sendKeys(Keys.ARROW_DOWN);
					break;
					case "PGUP":
					case "PAGE_UP":
						element.sendKeys(Keys.PAGE_UP);
					break;
					case "PGDN":
					case "PAGE_DOWN":
						element.sendKeys(Keys.PAGE_DOWN);
						break;
					case "BKSP":
					case "BACKSPACE":
						element.sendKeys(Keys.BACK_SPACE);
						break;
					case "DEL":
					case "DELETE":
						element.sendKeys(Keys.DELETE);
						break;
					case "TAB":
						element.sendKeys(Keys.TAB);
						break;
				}
			}
			else if(azione.equals("doubleClick")){
				identifyElement(currCommand,driver).click();
				identifyElement(currCommand,driver).click();
			}
			else if(azione.equals("storeTitle"))vars.put(currCommand.getTarget(), driver.getTitle());
			else if(azione.equals("storeText"))vars.put(currCommand.getTarget(),identifyElement(currCommand,driver).getText());
			else if(azione.equals("storeValue"))vars.put(currCommand.getTarget(),identifyElement(currCommand,driver).getAttribute(currCommand.getValue()));
			else if(azione.equals("assert"))assertEquals(vars.get(currCommand.getTarget()).toString(), currCommand.getValue());
			else if(azione.equals("assertAlert"))assertThat(driver.switchTo().alert().getText(), is(currCommand.getTarget()));
			else if(azione.equals("assertChecked"))assertTrue(identifyElement(currCommand,driver).isSelected());
			else if(azione.equals("assertConfermation"))assertThat(driver.switchTo().alert().getText(), is(currCommand.getTarget()));
			else if(azione.equals("assertEditable")){
				{
					WebElement element = identifyElement(currCommand,driver);
					Boolean isEditable = element.isEnabled() && element.getAttribute("readonly") == null;
					assertTrue(isEditable);
				}
			}
			else if(azione.equals("assertElementPresent")){
				{
					List<WebElement> elements = identifyElements(currCommand,driver);
					assert(elements.size() > 0);
				}
			}
			else if(azione.equals("assertPrompt"))assertThat(driver.switchTo().alert().getText(), is(currCommand.getTarget()));
			else if(azione.equals("assertSelectedValue")) {
				{
					String value = identifyElement(currCommand, driver).getAttribute("value");
					assertThat(value, is(currCommand.getValue()));
				}
			}
			else if(azione.equals("assertSelectedLabel")){
				{
					WebElement element = identifyElement(currCommand,driver);
					String value = element.getAttribute("value");
					String locator = String.format("option[@value='%s']", value);
					String selectedText = element.findElement(By.xpath(locator)).getText();
					assertThat(selectedText, is(currCommand.getValue()));
				}
			}
			else if(azione.equals("assertText"))assertThat(identifyElement(currCommand,driver).getText(), is(currCommand.getValue()));
			else if(azione.equals("assertTitle"))assertThat(driver.getTitle(), is(currCommand.getTarget()));
			else if(azione.equals("assertValue"))    {
				{
					String value = identifyElement(currCommand,driver).getAttribute("value");
					assertThat(value, is(currCommand.getValue()));
				}
			}
			else if(azione.equals("assertNotChecked"))assertFalse(identifyElement(currCommand,driver).isSelected());
			else if(azione.equals("assertNotEditable")){
				{
					WebElement element = identifyElement(currCommand,driver);
					Boolean isEditable = element.isEnabled() && element.getAttribute("readonly") == null;
					assertFalse(isEditable);
				}
			}
			else if(azione.equals("assertNotSelectedValue")) {
				{
					String value = identifyElement(currCommand, driver).getAttribute("value");
					assertThat(value, is(not(currCommand.getValue())));
				}
			}
			else if(azione.equals("assertNotText"))assertThat(identifyElement(currCommand,driver).getText(), is(not(currCommand.getValue())));
			else if(azione.equals("assertElementNotPresent")){
				{
					List<WebElement> elements = identifyElements(currCommand,driver);
					assert(elements.size() == 0);
				}
			}
		}
		driver.quit();
		return listener.getHtmlPages();
	}

	private WebElement identifyElement(Command commandToIdentify, WebDriver driver){
		String[] target=commandToIdentify.getTarget().split("=",2);
		System.out.println(target[0]+target[1]);
		switch(target[0]){
			case "css":
				return driver.findElement(By.cssSelector(target[1]));
			case "id":
				return driver.findElement(By.id(target[1]));
			case "name":
				return driver.findElement(By.name(target[1]));
			case "xpath":
				return driver.findElement(By.xpath(target[1]));
			case "linkText":
				return driver.findElement(By.linkText(target[1]));
			case "className":
				return driver.findElement(By.className(target[1]));
			case "partialLinkText":
				return driver.findElement(By.partialLinkText(target[1]));

			default: throw new InvalidArgumentException("Web Element non trovato!");
		}
	}

	private By identifyBy(Command commandToIdentify, WebDriver driver){
		String[] target=commandToIdentify.getTarget().split("=",2);
		switch(target[0]){
			case "css":
				return By.cssSelector(target[1]);
			case "id":
				return By.id(target[1]);
			case "name":
				return By.name(target[1]);
			case "xpath":
				return By.xpath(target[1]);
			case "linkText":
				return By.linkText(target[1]);
			case "className":
				return By.className(target[1]);
			case "partialLinkText":
				return By.partialLinkText(target[1]);

			default: throw new InvalidArgumentException("Web Element non trovato!");
		}
	}

	private List<WebElement> identifyElements(Command commandToIdentify, WebDriver driver){
		String[] target=commandToIdentify.getTarget().split("=",2);
		switch(target[0]){
			case "css":
				return driver.findElements(By.cssSelector(target[1]));
			case "id":
				return driver.findElements(By.id(target[1]));
			case "name":
				return driver.findElements(By.name(target[1]));
			case "xpath":
				return driver.findElements(By.xpath(target[1]));
			case "linkText":
				return driver.findElements(By.linkText(target[1]));
			case "className":
				return driver.findElements(By.className(target[1]));
			case "partialLinkText":
				return driver.findElements(By.partialLinkText(target[1]));

			default: throw new InvalidArgumentException("Web Element non trovato!");
		}
	}
}
