package org.springframework.samples.petclinic;

import com.mifmif.common.regex.Generex;
import javafx.util.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.utility.CatchPageWebDriverEventListener;
import org.springframework.samples.petclinic.utility.StringSimilarity;
import org.springframework.samples.petclinic.utility.Triple;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.util.*;

import static org.springframework.samples.petclinic.utility.StringSimilarity.printSimilarity;
import static org.springframework.samples.petclinic.utility.StringSimilarity.similarity;

public class PetClinicTests {

	public static  void main(String[] args) {

		// To do:
		// foto se poss

		final Integer NUMBER_OF_FOLLOW_UP=3;
		TestCaseCreator crea = new TestCaseCreator();
		List<TestCase> testCases = crea.CreateFromJSON("C:/Users/ivan_/spring-petclinic/testFile/PetclinicAddPet.side");
		//List<TestCase> testCases = crea.CreateFromJSON("C:/Users/ivan_/spring-petclinic/testFile/ForumUninaTest.side");
		TestRunner run = new TestRunner();
		Map<Pair<List<WebPage>,List<File>>,List<Triple<TestCase,List<WebPage>,List<File>>>> allTestCases=new HashMap<>();
		// Creazione delle regole
		List<Rule> regole= new ArrayList<>();
		Rule regola1=new Rule("http://localhost:8080/owners\\?lastName=","click","linkText=Mario Rossi",
			"linkText=Mario Rossi|linkText=Ciro Esposito|linkText=Mario Bianchi|linkText=Michele Russo|linkText=Francesca Ferrara","SELECTOR");
		Rule regola2=new Rule("http://localhost:8080/owners/[0-9]*/pets/new","type","id=name","([A-z]|[0-9]){10}[?!]","VALUES");
		Rule regola3=new Rule("https://www.informatica-unina.com/forum/index.php?sid=7ac8139d87b0417a85f9526a5fe19f50","click","linkText=Ingegneria del Software I",
			"linkText=Algebra | linkText=Fisica Generale I | linkText=Algoritmi e Strutture Dati I","SELECTOR");
		Rule regola4= new Rule("https://www.informatica-unina.com/forum/.*","click","id=message","id=subject","SELECTOR");
		Rule regola5= new Rule("https://www.informatica-unina.com/forum/.*","type","id=message","id=subject"," (0% Ironico!)","BOTH");

		regole.add(regola1);
		regole.add(regola2);
		regole.add(regola3);
		regole.add(regola4);
		regole.add(regola5);
		for (TestCase testCase : testCases) {
			// Source test case
			Pair<List<WebPage>,List<File>> htmlPages = run.testRunner(testCase);
			List<Triple<WebPage,Boolean,File>> listHtmlPages=new ArrayList<>();
			Iterator<WebPage> iterHtmlPages=htmlPages.getKey().iterator();
			Iterator<File> iterHtmlPhoto=htmlPages.getValue().iterator();
			while(iterHtmlPages.hasNext() && iterHtmlPhoto.hasNext())
				listHtmlPages.add(new Triple<>(iterHtmlPages.next(),false,iterHtmlPhoto.next()));
			testCase.createFile("Source",listHtmlPages);
			System.out.println("Pagine visitate Source: "+htmlPages.getKey().size()+ htmlPages.getValue().size());
			List<Triple<TestCase, List<WebPage>,List<File>>> followUpContainer=new ArrayList<>();
			for(int i = 1; i<=NUMBER_OF_FOLLOW_UP; i++) {
				//Follow-Up test cases
				System.out.println("Follow-Up N."+i+" in esecuzione ...");
				Triple<TestCase, List<WebPage>,List<File>> followUp = run.createFollowupTestCase(testCase, regole);
				followUpContainer.add(followUp);
				System.out.println("Pagine visitate followUp: " + followUp.getSecond().size());
			}
			allTestCases.put(htmlPages,followUpContainer);
		}
		//Confronto delle pagine
		Set<Pair<List<WebPage>,List<File>>> sourceTestCase=allTestCases.keySet();
		for (Pair<List<WebPage>,List<File>> test: sourceTestCase){
			for(Triple<TestCase,List<WebPage>,List<File>> follow_Up : allTestCases.get(test)){
				TestCase testCaseFollowUp=follow_Up.getFirst();
				Iterator<WebPage> iterFollowUp=follow_Up.getSecond().iterator();
				Iterator<File> iterFollowUpFile=follow_Up.getThird().iterator();
				Iterator<WebPage> iterSource=test.getKey().iterator();
				List<Triple<WebPage,Boolean,File>> warningPages=new ArrayList<>();
				while(iterSource.hasNext() && iterFollowUp.hasNext() && iterFollowUpFile.hasNext()){
					WebPage sourceWebPage=iterSource.next();
					WebPage followUpWebPage=iterFollowUp.next();
					File followUpFile=iterFollowUpFile.next();
					String pageHtmlSource=sourceWebPage.getHtmlPage();
					String pageHtmlFollowUp=followUpWebPage.getHtmlPage();
					if(similarity(pageHtmlSource,pageHtmlFollowUp) < StringSimilarity.SIMILARITYOFTWOPAGE) {
						System.out.println("Le due pagine sono troppo diverse!");
						if(!testCaseFollowUp.getFaulty() && !testCaseFollowUp.getWarning())testCaseFollowUp.setWarning(true);
						warningPages.add(new Triple<>(followUpWebPage,true,followUpFile));
						printSimilarity(pageHtmlSource,pageHtmlFollowUp);
					}
					else warningPages.add(new Triple<>(followUpWebPage,false,followUpFile));
				}
				if(testCaseFollowUp.getFaulty()) testCaseFollowUp.createFile("Faulty",warningPages);
				else if(testCaseFollowUp.getWarning()) testCaseFollowUp.createFile("Warning",warningPages);
				else testCaseFollowUp.createFile("Success",warningPages);
			}
		}
	}

	public static List<List<WebPage>> executeTests(List<String> classNames, List<String> methodNames)
			throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		// Variabili per il Class Loader
		Class[] arg = new Class[1];
		arg[0] = WebDriver.class;
		Class petX;
		Method method1;
		Method method2;
		Method method3;
		Object objPetX;
		// Variabili per l'iterazione
		String nomeClasse;
		String nomeMetodo;
		WebDriver driver;
		EventFiringWebDriver driverEvent;
		CatchPageWebDriverEventListener listener;
		List<WebPage> htmlPages;
		List<List<WebPage>> testPages = new ArrayList<>();

		if (classNames.size() != methodNames.size())
			throw new IllegalArgumentException();
		for (Integer testNum = 1; testNum <= classNames.size(); testNum++) {
			// Prossimi test da eseguire
			nomeClasse = classNames.get(testNum - 1);
			nomeMetodo = methodNames.get(testNum - 1);
			// Creazione driver
			driver = new ChromeDriver();
			driverEvent = new EventFiringWebDriver(driver);
			listener = new CatchPageWebDriverEventListener();
			driver = driverEvent.register(listener);

			// Esecuzione test case
			petX = Class.forName(nomeClasse);
			objPetX = petX.getDeclaredConstructor(arg).newInstance(driver);
			method1 = petX.getDeclaredMethod("setUp");
			method1.invoke(objPetX);
			method2 = petX.getDeclaredMethod(nomeMetodo);
			method2.invoke(objPetX);
			method3 = petX.getDeclaredMethod("tearDown");
			method3.invoke(objPetX);
			// Risultato del test
			htmlPages = listener.getHtmlPages();
			testPages.add(htmlPages);
		}
		return testPages;
	}

}
