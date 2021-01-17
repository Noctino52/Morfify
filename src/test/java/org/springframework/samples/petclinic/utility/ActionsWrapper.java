package org.springframework.samples.petclinic.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActionsWrapper extends Actions {

	public CatchPageWebDriverEventListener listener;
	public WebElement element;
	public WebDriver driver;

	public ActionsWrapper(WebDriver driver, CatchPageWebDriverEventListener listener) {
		super(driver);
		this.listener=listener;
		this.driver=driver;
	}

	@Override
	public Actions moveToElement(WebElement target) {
		this.element=target;
		return super.moveToElement(target);
	}

	@Override
	public ActionWrapper build() {
		return new ActionWrapper(super.build(),listener,driver);
	}
}
