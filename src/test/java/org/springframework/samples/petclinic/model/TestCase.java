package org.springframework.samples.petclinic.model;

import java.util.List;

public class TestCase {
	private String id;
	private String name;
	private String url;
	private List<Command> commands;


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
}
