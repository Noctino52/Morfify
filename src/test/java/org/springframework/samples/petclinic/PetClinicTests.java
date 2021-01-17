package org.springframework.samples.petclinic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.samples.petclinic.model.TestCase;
import org.springframework.samples.petclinic.model.TestCaseCreator;
import org.springframework.samples.petclinic.model.TestRunner;
import org.springframework.samples.petclinic.utility.CatchPageWebDriverEventListener;
import org.springframework.samples.petclinic.model.WebPage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PetClinicTests {

	public static void main(String[] args)  {
		//Creazione classi (?)

		TestCaseCreator crea=new TestCaseCreator();
		List<TestCase> testCases=crea.CreateFromJSON("C:/Users/ivan_/spring-petclinic/testFile/Prova.side");
		TestRunner run=new TestRunner();
		for (TestCase testCase:testCases) {
			List<WebPage> htmlPages=run.testRunner(testCase);
			System.out.println(htmlPages.size());
		}

		//metodo(FileJSON)
		//Classe testRunner
		//tutto su github

		//Elenco classi e metodi
		/*List<String> classNames=new ArrayList<>();
		List<String> methodNames=new ArrayList<>();
		List<List<WebPage>> testPages = null;

		classNames.add("org.springframework.samples.petclinic.pet.create.mouse.CreatePetMouse");
		classNames.add("org.springframework.samples.petclinic.pet.create.keyboard.CreatePetKeyboard");
		classNames.add("org.springframework.samples.petclinic.pet.update.mouse.UpdatePetMouse");
		classNames.add("org.springframework.samples.petclinic.pet.update.keyboard.UpdatePetKeyboard");

		methodNames.add("createPetMouseSource");
		methodNames.add("createPetKeyboardSource");
		methodNames.add("updatePetMouseSource");
		methodNames.add("updatePetKeyboardSource");

		//Esecuzione dei test
		try {
			testPages=executeTests(classNames,methodNames);
		} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}

		//Stampa risultati
		for (List<WebPage> listPage:testPages) {
			for (WebPage page: listPage) {
				System.out.println(page);
			}
			System.out.println(listPage.size());
		}
		 */
	}

	public static List<List<WebPage>> executeTests(List<String> classNames,List<String> methodNames) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		//Variabili per il Class Loader
		Class[] arg=new Class[1];
		arg[0]=WebDriver.class;
		Class petX;
		Method method1;
		Method method2;
		Method method3;
		Object objPetX;
		//Variabili per l'iterazione
		String nomeClasse;
		String nomeMetodo;
		WebDriver driver;
		EventFiringWebDriver driverEvent;
		CatchPageWebDriverEventListener listener;
		List<WebPage> htmlPages;
		List<List<WebPage>> testPages=new ArrayList<>();

		if(classNames.size()!=methodNames.size())throw new IllegalArgumentException();
		for(Integer testNum=1;testNum<=classNames.size();testNum++) {
			//Prossimi test da eseguire
			nomeClasse=classNames.get(testNum-1);
			nomeMetodo=methodNames.get(testNum-1);
			//Creazione driver
			driver = new ChromeDriver();
			driverEvent = new EventFiringWebDriver(driver);
			listener = new CatchPageWebDriverEventListener();
			driver = driverEvent.register(listener);

			//Esecuzione test case
			petX = Class.forName(nomeClasse);
			objPetX = petX.getDeclaredConstructor(arg).newInstance(driver);
			method1 = petX.getDeclaredMethod("setUp");
			method1.invoke(objPetX);
			method2 = petX.getDeclaredMethod(nomeMetodo);
			method2.invoke(objPetX);
			method3 = petX.getDeclaredMethod("tearDown");
			method3.invoke(objPetX);
			//Risultato del test
			htmlPages = listener.getHtmlPages();
			testPages.add(htmlPages);
		}
		return testPages;
	}


}
