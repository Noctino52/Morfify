package org.springframework.samples.petclinic.model;

import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

public class TestCaseCreator {

	public List<TestCase> CreateFromJSON(String jsonPathFile) {
		List<TestCase> testCases = new ArrayList<>();
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(jsonPathFile));
		}
		catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		JSONObject testObj = (JSONObject) obj;
		JSONArray jsonTests = (JSONArray) testObj.get("tests");
		Iterator<JSONObject> iterTests = jsonTests.iterator();
		while (iterTests.hasNext()) {
			// itero sui test case nel file
			JSONObject testCaseJSON = iterTests.next();
			TestCase testCase = new TestCase();
			// Passaggio da testJSON a Test Case
			testCase.setUrl((String) testObj.get("url"));
			testCase.setName((String) testCaseJSON.get("name"));
			// Retrieving command
			List<Command> risCommand = new ArrayList<>();
			JSONArray jsonCommands = (JSONArray) testCaseJSON.get("commands");
			Iterator<JSONObject> listCommands = jsonCommands.iterator();
			while (listCommands.hasNext()) {
				JSONObject commandJSON = listCommands.next();
				// gestione targets
				List<Pair<String, String>> listAlternative = new ArrayList<>();
				JSONArray jsonAlternative = (JSONArray) commandJSON.get("targets");
				Iterator<JSONArray> iterOfAlternative = jsonAlternative.iterator();
				while (iterOfAlternative.hasNext()) {
					JSONArray pairJSON = iterOfAlternative.next();
					if(pairJSON.size()>1)listAlternative.add(new Pair<String, String>((String) pairJSON.get(0), (String) pairJSON.get(1)));
					else listAlternative.add(new Pair<String, String>((String) pairJSON.get(0), null));
				}
				// Creazione command
				Command command = new Command((String) commandJSON.get("comment"), (String) commandJSON.get("command"),
						(String) commandJSON.get("target"), (String) commandJSON.get("value"), listAlternative);
				risCommand.add(command);
			}
			testCase.setCommands(risCommand);
			// Aggiungo il test appena finito di settare nella lista da restituire
			testCases.add(testCase);
			// Testing purpose
			/*
			 * System.out.println(testCase.getName());
			 * System.out.println(testCase.getUrl()); for (Command
			 * command:testCase.getCommands()){ System.out.println(command.getCommand());
			 * System.out.println(command.getComment());
			 * System.out.println(command.getTarget());
			 * System.out.println(command.getValue()); for (Pair<String,String>
			 * alternative:command.getTargets()) {
			 * System.out.println(alternative.getKey());
			 * System.out.println(alternative.getValue()); } }
			 */
		}
		return testCases;
	}

}
