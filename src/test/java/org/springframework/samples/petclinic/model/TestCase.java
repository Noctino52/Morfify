package org.springframework.samples.petclinic.model;

import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.springframework.samples.petclinic.utility.Triple;

import java.io.*;
import java.net.URI;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TestCase implements Serializable {
	private static final String DIRECTORY_OUT_SOURCE = "C:\\Users\\ivan_\\spring-petclinic\\Output\\Source";
	private static final String DIRECTORY_OUT_FOLLOW = "C:\\Users\\ivan_\\spring-petclinic\\Output\\Follow-Up";

	private String id;

	private String name;

	private String url;

	private List<Command> commands;

	private Boolean isFollowUp;
	private final AtomicLong followUpCounter = new AtomicLong();
	private static final AtomicLong idCounter = new AtomicLong();

	private static int numSuccess;
	private static int numWarning;
	private static int numFaulty;
	private static int numCurr;
	private static int numSource;

	private Boolean isFaulty = false;
	private Boolean isWarning = false;


	public TestCase() {
		super();
		this.id = String.valueOf(idCounter.getAndIncrement() + ":0");
		isFollowUp = false;
	}

	public TestCase(TestCase toCopy) {
		this.id = new String(toCopy.id + ":" + followUpCounter.getAndIncrement());
		this.name = new String(toCopy.name);
		this.url = new String(toCopy.url);
		this.commands = new ArrayList<Command>(toCopy.commands);
		this.isFollowUp = true;
	}

	public void createFile(String type, List<Triple<WebPage, Boolean,File>> warningPages) {
		String path;
		if (type.equals("Source")) {
			numSource += 1;
			numCurr=0;
			path = DIRECTORY_OUT_SOURCE + "//" + numSource+"//";
		} else path = DIRECTORY_OUT_FOLLOW + "//Source-N." + numSource + "//" + (++numCurr) + "//";
		saveFile(path, type, warningPages);
		System.out.printf("\n Serialized data is saved in " + path);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getFollowUp() {
		return isFollowUp;
	}

	public void setFollowUp(Boolean followUp) {
		isFollowUp = followUp;
	}

	@Override
	public String toString() {
		return "TestCase\n {" +
			"\n id='" + id +
			"\n name='" + name +
			"\n url='" + url +
			"\n commands=" + commands +
			"\n } \n";
	}

	public Boolean getFaulty() {
		return isFaulty;
	}

	public void setFaulty(Boolean Faulty) {
		isFaulty = Faulty;
	}

	public Boolean getWarning() {
		return isWarning;
	}

	public void setWarning(Boolean Warning) {
		isWarning = Warning;
	}

	private void saveFile(String path, String type, List<Triple<WebPage, Boolean,File>> warningPages) {
		try {
			File theDir = new File(path);
			if (!theDir.exists()) {
				theDir.mkdirs();
			}
			FileOutputStream fileOut;
			if(type.equals("Source"))fileOut = new FileOutputStream(path + "//TestCase"+numSource);
			else fileOut = new FileOutputStream(path + "//TestCase"+numCurr);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.write(this.toString().getBytes());
			if (type.equals("Success")) {
				out.write("\nTest Successfully done: \n".getBytes());
				out.write("All pages are similar to the Source test case.".getBytes());
			} else if (type.equals("Warning")) out.write("\nTest Successfully done, but some pages are not similar with the Source test case: \n \n".getBytes());
			else if (type.equals("Faulty")) out.write("\nTest not successfully done, probably some exception have been throw: \n".getBytes());
			else if(type.equals("Source")) out.write("\nThis is a Source Test Case.\n".getBytes());
			out.flush();
			out.close();
			fileOut.close();
			Iterator<Triple<WebPage, Boolean,File>> pageIter = warningPages.iterator();
			int i = 0;
			while (pageIter.hasNext()) {
				i = i + 1;
				Triple<WebPage, Boolean,File> page = pageIter.next();
				File DestFile=new File(path+i+".png");
				//Copy file at destination
				FileUtils.copyFile(page.getThird(), DestFile);
				if (page.getSecond()) fileOut = new FileOutputStream(path + i + "!.html");
				else fileOut = new FileOutputStream(path + i + ".html");
				out = new ObjectOutputStream(fileOut);
				out.write(page.getFirst().toString().getBytes());
				out.flush();
				out.close();
				fileOut.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
