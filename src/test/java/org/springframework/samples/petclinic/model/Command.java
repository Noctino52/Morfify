package org.springframework.samples.petclinic.model;

import com.sun.tools.javac.util.Pair;

import java.util.List;

public class Command {
	private String id;
	private String comment;
	private String command;
	private String target;
	private List<List<Pair<String,String>>> targets;

	public Command(String comment, String command, String target, List<List<Pair<String, String>>> targets) {
		this.comment = comment;
		this.command = command;
		this.target = target;
		this.targets = targets;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<List<Pair<String, String>>> getTargets() {
		return targets;
	}

	public void setTargets(List<List<Pair<String, String>>> targets) {
		this.targets = targets;
	}
}
