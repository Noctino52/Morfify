package org.springframework.samples.petclinic.utility;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;

public class ActionWrapper implements Action {

	Action action;
	public CatchPageWebDriverEventListener listener;
	WebDriver driver;

	public ActionWrapper(Action action,CatchPageWebDriverEventListener listener,WebDriver driver){
		this.action=action;
		this.listener=listener;
		this.driver=driver;
	}

	@Override
	public void perform() {
		action.perform();
	}
}
