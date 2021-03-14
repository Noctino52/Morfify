package org.springframework.samples.petclinic.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TestCase implements Serializable {

	private String id;

	private String name;

	private String url;

	private List<Command> commands;

	private Boolean isFollowUp;
	private final AtomicLong followUpCounter=new AtomicLong();
	private static final AtomicLong idCounter = new AtomicLong();

	private static int numSuccess;
	private static int numFaulty;
	private static int numWarning;

	private Boolean isWarning=false;
	private Boolean isFaulty=false;


	public TestCase() {
		super();
		this.id=String.valueOf(idCounter.getAndIncrement()+":0");
		isFollowUp=false;
	}

	public TestCase(TestCase toCopy) {
		this.id = new String(toCopy.id+":"+followUpCounter.getAndIncrement());
		this.name = new String(toCopy.name);
		this.url = new String(toCopy.url);
		this.commands = new ArrayList<Command>(toCopy.commands);
		this.isFollowUp = true;
	}

	public FileOutputStream createFile(String type,List<WebPage> faultyPages){
		FileOutputStream fileOut = null;
		String path=null;
		if(type.equals("Success"))path= "C:\\Users\\ivan_\\spring-petclinic\\Output\\" + type + "\\TestCase"+(++numSuccess)+".ser";
		else if(type.equals("Faulty"))path="C:\\Users\\ivan_\\spring-petclinic\\Output\\" + type + "\\TestCase"+(++numFaulty)+".ser";
		else if(type.equals("Warning"))path="C:\\Users\\ivan_\\spring-petclinic\\Output\\" + type + "\\TestCase"+(++numWarning)+".ser";
		try {
			fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.write(this.toString().getBytes());
			if(type.equals("Success")){
				out.write("\nTest Successfully done: \n".getBytes());
				out.write("All pages are similar to the Source test case.".getBytes());
			}
			else if(type.equals("Faulty")){
				out.write("\nTest Successfully done, but some pages are not similar with the Source test case: \n \n".getBytes());
				out.write(faultyPages.toString().getBytes());
			}
			else if(type.equals("Warning")){
				out.write("\nTest not successfully done, probably some exception have been throw: \n".getBytes());
			}
			out.flush();
			out.close();
			fileOut.close();
			System.out.printf("\n Serialized data is saved in "+path);
		} catch (IOException i) {
			i.printStackTrace();
		}
		return fileOut;
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
			"\n name='" + name+
			"\n url='" + url +
			"\n commands=" + commands +
			"\n } \n";
	}

	public Boolean getWarning() {
		return isWarning;
	}

	public void setWarning(Boolean warning) {
		isWarning = warning;
	}

	public Boolean getFaulty() {
		return isFaulty;
	}

	public void setFaulty(Boolean faulty) {
		isFaulty = faulty;
	}
}
