package org.springframework.samples.petclinic.model;

import org.openqa.selenium.InvalidArgumentException;

import java.util.Objects;

public class Rule {

	private final String urlTarget;

	private final String commandMatch;

	private final String targetMatch;

	private final String mutationType;

	private final String patternValue;

	private final String patternTarget;

	public Rule(String urlTarget, String commandMatch, String targetMatch, String patternValue, String mutationType) {
		this.urlTarget = urlTarget;
		this.commandMatch = commandMatch;
		this.targetMatch = targetMatch;
		if (mutationType.equals("VALUES")) {
			this.mutationType = mutationType;
			this.patternValue = patternValue;
			this.patternTarget = null;
		}
		else if (mutationType.equals("SELECTOR")) {
			this.mutationType = mutationType;
			this.patternTarget = patternValue;
			this.patternValue = null;
		}
		else if (mutationType.equals("BOTH"))
			throw new IllegalArgumentException("You should use the other constructor");
		else
			throw new InvalidArgumentException("Use mutationType: VALUES OR SELECTED OR BOTH");
	}

	public Rule(String urlTarget, String commandMatch, String targetMatch, String patternValue, String patternTarget,
			String mutationType) {
		this.urlTarget = urlTarget;
		this.commandMatch = commandMatch;
		this.targetMatch = targetMatch;
		if (mutationType.equals("VALUES") || mutationType.equals("SELECTOR"))
			throw new IllegalArgumentException("You should use the other constructor");
		else if (mutationType.equals("BOTH")) {
			this.mutationType = mutationType;
			this.patternValue = patternValue;
			this.patternTarget = patternTarget;
		}
		else
			throw new InvalidArgumentException("Use mutationType: VALUES OR SELECTED OR BOTH");
	}

	public String getUrlTarget() {
		return urlTarget;
	}

	public String getCommandMatch() {
		return commandMatch;
	}

	public String getTargetMatch() {
		return targetMatch;
	}

	public String getMutationType() {
		return mutationType;
	}

	public String getPatternValue() {
		return patternValue;
	}

	public String getPatternTarget() {
		return patternTarget;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Rule))
			return false;
		Rule rule = (Rule) o;
		return urlTarget.equals(rule.urlTarget) && commandMatch.equals(rule.commandMatch)
				&& targetMatch.equals(rule.targetMatch) && mutationType.equals(rule.mutationType)
				&& Objects.equals(patternValue, rule.patternValue) && Objects.equals(patternTarget, rule.patternTarget);
	}

	@Override
	public int hashCode() {
		return Objects.hash(urlTarget, commandMatch, targetMatch, mutationType, patternValue, patternTarget);
	}

	@Override
	public String toString() {
		return "Rule{" + "urlTarget='" + urlTarget + '\'' + ", commandMatch='" + commandMatch + '\'' + ", targetMatch='"
				+ targetMatch + '\'' + ", mutationType='" + mutationType + '\'' + ", patternValue='" + patternValue
				+ '\'' + ", patternTarget='" + patternTarget + '\'' + '}';
	}

}
