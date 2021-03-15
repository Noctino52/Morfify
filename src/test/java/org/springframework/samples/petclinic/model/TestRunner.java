package org.springframework.samples.petclinic.model;

import com.mifmif.common.regex.Generex;
import javafx.util.Pair;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.samples.petclinic.utility.ActionsWrapper;
import org.springframework.samples.petclinic.utility.CatchPageWebDriverEventListener;
import org.springframework.samples.petclinic.utility.Triple;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TestRunner {

	private WebDriver driver;

	private CatchPageWebDriverEventListener listener;

	private Map<String, Object> vars;

	private JavascriptExecutor js;

	private WebDriverWait wait;

	public TestRunner() {
	}

	public Pair<List<WebPage>,List<File>> testRunner(TestCase test) {
		// Creazione driver
		initRunner();

		for (Command currCommand : test.getCommands()) {
			executeCommand(test, currCommand);
		}
		driver.quit();
		return new Pair<>(listener.getHtmlPages(),listener.getScreenshotPages());
	}

	public Triple<TestCase, List<WebPage>,List<File>> createFollowupTestCase(TestCase testSource, List<Rule> rules) {
		// Creazione driver etc...
		initRunner();

		TestCase followUp = new TestCase(testSource);

		for (Command currCommand : followUp.getCommands()) {
			// Fa il match delle regole e restituisce la modifica della prima regola a
			// fare il match
			verifyRules(rules, currCommand);
			// Eseguo il comando modificato, sto eseguendo il followUp
			executeCommand(followUp, currCommand);
			try {
				if (driver.getTitle() == null) followUp.setWarning(true);
			}
			catch(WebDriverException ignored){

			}
		}

		driver.quit();
		return new Triple<TestCase, List<WebPage>,List<File>>(followUp, listener.getHtmlPages(),listener.getScreenshotPages());
	}

	private void verifyRules(List<Rule> rules, Command currCommand) {
		for (Rule rule : rules) {
			//Match URL
			Pattern  patternUrl= Pattern.compile(rule.getUrlTarget());
			Matcher matchUrl=patternUrl.matcher(driver.getCurrentUrl());
				if (matchUrl.matches()){
					//Match Command
					Pattern  patternCommand= Pattern.compile(rule.getCommandMatch());
					Matcher matchCommand=patternCommand.matcher(currCommand.getCommand());
					if (matchCommand.matches()){
						//Match Target
						Pattern  patternTarget= Pattern.compile(rule.getTargetMatch());
						Matcher matchTarget=patternTarget.matcher(currCommand.getTarget());
						if (matchTarget.matches()){
									//Verify Type of mutation and set
									if (rule.getMutationType().equals("VALUES")) {
										Generex generex = new Generex(rule.getPatternValue());
										currCommand.setValue(generex.random());
									} else if (rule.getMutationType().equals("SELECTOR")) {
										Generex generex = new Generex(rule.getPatternTarget());
										currCommand.setTarget(generex.random());
									} else if (rule.getMutationType().equals("BOTH")) {
										Generex generexTargetMu = new Generex(rule.getPatternTarget());
										Generex generexValueMu = new Generex(rule.getPatternValue());
										currCommand.setValue(generexTargetMu.random());
										currCommand.setValue(generexValueMu.random());
									}
									//Prima regola soddisfatta
									break;
					}
				}
			}
		}
	}

	private void initRunner() {
		driver = new ChromeDriver();
		EventFiringWebDriver driverEvent = new EventFiringWebDriver(driver);
		listener = new CatchPageWebDriverEventListener();
		driver = driverEvent.register(listener);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	}

	private WebElement identifyElement(Command commandToIdentify, WebDriver driver) {
		String[] target = commandToIdentify.getTarget().split("=", 2);
		switch (target[0]) {
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

		default:
			throw new InvalidArgumentException("Web Element non trovato!");
		}
	}

	private By identifyBy(Command commandToIdentify, WebDriver driver) {
		String[] target = commandToIdentify.getTarget().split("=", 2);
		switch (target[0]) {
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

		default:
			throw new InvalidArgumentException("Web Element non trovato!");
		}
	}

	private List<WebElement> identifyElements(Command commandToIdentify, WebDriver driver) {
		String[] target = commandToIdentify.getTarget().split("=", 2);
		switch (target[0]) {
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

		default:
			throw new InvalidArgumentException("Web Element non trovato!");
		}
	}

	private void executeCommand(TestCase test, Command currCommand) {
		String azione = currCommand.getCommand();
		switch (azione) {
		case "open":
			driver.get(test.getUrl());
			break;
		case "setWindowSize":
			String[] size = currCommand.getTarget().split("x", 2);
			driver.manage().window().setSize(new Dimension(Integer.parseInt(size[0]), Integer.parseInt(size[1])));
			break;
		case "click": {
			WebElement element = identifyElement(currCommand, driver);
			js.executeScript("arguments[0].click();", element);
			break;
		}
		case "type": {
			WebElement element = identifyElement(currCommand, driver);
			element.clear();
			element.sendKeys(currCommand.getValue());
			break;
		}
		case "sendKeys": {
			WebElement element = identifyElement(currCommand, driver);
			String[] specialCharacter = currCommand.getValue().split("_", 2);
			switch (specialCharacter[1]) {
			case "ENTER}":
				element.sendKeys(Keys.ENTER);
				break;
			case "CTRL":
				element.sendKeys(Keys.CONTROL);
				break;
			case "LEFT":
				element.sendKeys(Keys.ARROW_LEFT);
				break;
			case "RIGHT":
				element.sendKeys(Keys.ARROW_RIGHT);
				break;
			case "UP":
				element.sendKeys(Keys.ARROW_UP);
				break;
			case "DOWN":
				element.sendKeys(Keys.ARROW_DOWN);
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
			break;
		}
		case "doubleClick":
			identifyElement(currCommand, driver).click();
			identifyElement(currCommand, driver).click();
			break;
		case "storeTitle":
			vars.put(currCommand.getTarget(), driver.getTitle());
			break;
		case "storeText":
			vars.put(currCommand.getTarget(), identifyElement(currCommand, driver).getText());
			break;
		case "storeValue":
			vars.put(currCommand.getTarget(),
					identifyElement(currCommand, driver).getAttribute(currCommand.getValue()));
			break;
		case "assert":
			assertEquals(vars.get(currCommand.getTarget()).toString(), currCommand.getValue());
			break;
		case "assertAlert":
		case "assertPrompt":
		case "assertConfermation":
			assertThat(driver.switchTo().alert().getText(), is(currCommand.getTarget()));
			break;
		case "assertChecked":
			assertTrue(identifyElement(currCommand, driver).isSelected());
			break;
		case "assertEditable":
		case "verifyEditable": {
			{
				WebElement element = identifyElement(currCommand, driver);
				Boolean isEditable = element.isEnabled() && element.getAttribute("readonly") == null;
				assertTrue(isEditable);
			}
			break;
		}
		case "assertElementPresent":
		case "verifyElementPresent": {
			{
				List<WebElement> elements = identifyElements(currCommand, driver);
				assert (elements.size() > 0);
			}
			break;
		}
		case "verifyValue":
		case "verifySelectedValue":
		case "assertSelectedValue":
		case "assertValue": {
			{
				String value = identifyElement(currCommand, driver).getAttribute("value");
				assertThat(value, is(currCommand.getValue()));
			}
			break;
		}
		case "verifySelectedLabel":
		case "assertSelectedLabel": {
			{
				WebElement element = identifyElement(currCommand, driver);
				String value = element.getAttribute("value");
				String locator = String.format("option[@value='%s']", value);
				String selectedText = element.findElement(By.xpath(locator)).getText();
				assertThat(selectedText, is(currCommand.getValue()));
			}
			break;
		}
		case "verifyText":
		case "assertText":
			assertThat(identifyElement(currCommand, driver).getText(), is(currCommand.getValue()));
			break;
		case "verifyTitle":
		case "assertTitle":
			assertThat(driver.getTitle(), is(currCommand.getTarget()));
			break;
		case "verifyNotChecked":
		case "assertNotChecked":
			assertFalse(identifyElement(currCommand, driver).isSelected());
			break;
		case "verifyNotEditable":
		case "assertNotEditable": {
			{
				WebElement element = identifyElement(currCommand, driver);
				Boolean isEditable = element.isEnabled() && element.getAttribute("readonly") == null;
				assertFalse(isEditable);
			}
			break;
		}
		case "verifyNotSelectedValue":
		case "assertNotSelectedValue": {
			{
				String value = identifyElement(currCommand, driver).getAttribute("value");
				assertThat(value, is(not(currCommand.getValue())));
			}
			break;
		}
		case "verifyNotText":
		case "assertNotText":
			assertThat(identifyElement(currCommand, driver).getText(), is(not(currCommand.getValue())));
			break;
		case "verifyElementNotPresent":
		case "assertElementNotPresent": {
			{
				List<WebElement> elements = identifyElements(currCommand, driver);
				assert (elements.size() == 0);
			}
			break;
		}
		case "check": {
			{
				WebElement element = driver.findElement(By.cssSelector("p:nth-child(7)"));
				if (!element.isSelected()) {
					element.click();
				}
			}
			break;
		}
		case "clickAt": {
			identifyElement(currCommand, driver).click();
		}
		case "doubleClickAt": {
			{
				WebElement elementToDouble = identifyElement(currCommand, driver);
				elementToDouble.click();
				elementToDouble.click();
			}
			break;
		}
		case "dragAndDropToObject": {
			{
				Command tmp = new Command(currCommand.getComment(), currCommand.getCommand(), currCommand.getTarget(),
						currCommand.getValue(), currCommand.getTargets());
				tmp.setValue(currCommand.getTarget());
				WebElement dragged = identifyElement(currCommand, driver);
				WebElement dropped = identifyElement(tmp, driver);
				ActionsWrapper builder = new ActionsWrapper(driver, listener);
				builder.dragAndDrop(dragged, dropped).perform();
			}
			break;
		}
		case "editContent": {
			{
				WebElement element = identifyElement(currCommand, driver);
				js.executeScript("if(arguments[0].contentEditable === 'true') {arguments[0].innerText = '"
						+ currCommand.getValue() + "'}", element);
			}
		}
		case "mouseDownAt":
		case "mouseDown": {
			{
				WebElement element = identifyElement(currCommand, driver);
				ActionsWrapper builder = new ActionsWrapper(driver, listener);
				builder.moveToElement(element).clickAndHold().perform();
			}
			break;
		}
		case "mouseOver":
		case "mouseMoveAt": {
			{
				System.out.println("asdas+");
				WebElement element = identifyElement(currCommand, driver);
				Actions builder = new Actions(driver);
				builder.moveToElement(element).perform();
			}
			break;
		}
		case "mouseUp": {
			{
				WebElement element = identifyElement(currCommand, driver);
				ActionsWrapper builder = new ActionsWrapper(driver, listener);
				builder.moveToElement(element).release().perform();
			}
		}
			break;
		case "mouseUpAt": {
			WebElement element = identifyElement(currCommand, driver);
			ActionsWrapper builder = new ActionsWrapper(driver, listener);
			builder.moveToElement(element).release().perform();
		}
			break;
		case "select":
		case "removeSelection": {
			{
				identifyElement(currCommand, driver).click();
			}
			break;
		}
		case "selectFrame": {
			{
				String[] target = currCommand.getTarget().split("=", 2);
				if (target[1].equals("parent")) driver.switchTo().defaultContent();
				else driver.switchTo().frame(Integer.parseInt(target[1]));
			}
			break;
		}
		case "store": {
			vars.put(currCommand.getValue(), currCommand.getTarget());
			break;
		}
		case "storeAttribute": {
			{
				WebElement element = identifyElement(currCommand, driver);
				String attribute = element.getAttribute(currCommand.getTarget());
				vars.put(currCommand.getValue(), attribute);
			}
			break;
		}
		case "storeJSON": {
			vars.put(currCommand.getTarget(), driver.getWindowHandle());
			break;
		}
		case "storeWindowHandle": {
			vars.put(currCommand.getValue(), identifyElements(currCommand, driver).size());
			break;
		}
		case "uncheck": {
			WebElement element = identifyElement(currCommand, driver);
			if (element.isSelected()) {
				element.click();
			}
			break;
		}
		case "verify": {
			assertEquals(vars.get(currCommand.getTarget()).toString(), currCommand.getValue());
			break;
		}
		case "verifyChecked": {
			assertTrue(driver.findElement(identifyBy(currCommand, driver)).isSelected());
			break;
		}
		default: {
			throw new UnsupportedOperationException("Command "+azione+"Not supported!");
		}
		}
	}

}
