package org.springframework.samples.petclinic.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.internal.WebElementToJsonConverter;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.samples.petclinic.utility.CatchPageWebDriverEventListener;

public class TestRunner {


	public TestRunner(){
	}

	public void testRunner(TestCase test){
		//Creazione driver
		WebDriver driver = new ChromeDriver();
		EventFiringWebDriver driverEvent = new EventFiringWebDriver(driver);
		CatchPageWebDriverEventListener listener = new CatchPageWebDriverEventListener();
		driver = driverEvent.register(listener);

		for (Command currCommand:test.getCommands()) {
			String azione= currCommand.getCommand();
			//elenco azioni (aiutatemi)
			if(azione.equals("open"))driver.get(test.getUrl());
			else if(azione.equals("setWindowSize")){
				String[] size=currCommand.getTarget().split("x",2);
				driver.manage().window().setSize(new Dimension(Integer.parseInt(size[0]),Integer.parseInt(size[1])));
			}
			else if(azione.equals("click")){
				String typeOfElement=identifyElement(currCommand);
				WebElementToJsonConverter elem= new WebElementToJsonConverter();
				RemoteWebElement eleme=new RemoteWebElement();
				eleme.getCssValue()
				elem.
				if(typeOfElement.equals("CSS"))driver.findElement(By.cssSelector(currCommand.getTarget()))
			}

		}


	}

	private String identifyElement(Command commandToIdentify){

	}
}
