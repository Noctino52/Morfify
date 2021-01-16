package org.springframework.samples.petclinic.pet.update.mouse;// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class UpdatePetMouse {
	private final WebDriver driver;
	JavascriptExecutor js;

	public UpdatePetMouse(WebDriver driver){
		this.driver=driver;
	}

	@Before
	public void setUp() {
		js = (JavascriptExecutor) driver;
	}


	@After
	public void tearDown() {
		driver.quit();
	}
  @Test
  public void updatePetMouseSource() {
    driver.get("http://localhost:8080/");
    driver.manage().window().maximize();
    driver.findElement(By.cssSelector(".glyphicon-search")).click();
    driver.findElement(By.id("lastName")).click();
    driver.findElement(By.id("lastName")).sendKeys("Rossi");
    driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
    driver.findElement(By.cssSelector("td:nth-child(1) > a")).click();
    driver.findElement(By.cssSelector(".form-group > .form-group:nth-child(3)")).click();
    driver.findElement(By.id("birthDate")).clear();
    driver.findElement(By.cssSelector(".form-group:nth-child(4) > .col-sm-2")).click();
    driver.findElement(By.id("birthDate")).click();
    driver.findElement(By.id("birthDate")).sendKeys("1999-01-01");
    driver.findElement(By.id("type")).click();
    driver.findElement(By.id("type")).click();
    driver.findElement(By.cssSelector(".btn")).click();
  }
}
