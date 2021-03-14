package org.springframework.samples.petclinic.model;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.List;

public class Command implements Serializable {

	private String id;

	private String comment;

	private String command;

	private String target;

	private String value;

	private List<Command> commandIf;

	private List<Pair<String, String>> targets;

	public Command(String comment, String command, String target, String value, List<Pair<String, String>> targets) {
		this.comment = comment;
		this.command = command;
		this.target = target;
		this.value = value;
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

	public List<Pair<String, String>> getTargets() {
		return targets;
	}

	public void setTargets(List<Pair<String, String>> targets) {
		this.targets = targets;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return
			"command='" + command + '\'' +
			", target='" + target + '\'';
	}
}
