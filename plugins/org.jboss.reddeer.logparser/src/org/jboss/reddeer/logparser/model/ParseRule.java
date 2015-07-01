package org.jboss.reddeer.logparser.model;

public class ParseRule {
	private String name = "";
	private String description = "";
	private String includeRegex = "";
	private String excludeRegex = "";
	private int indent = 0;
	private String prefix = "";
	private int displayLinesBefore = 0;
	private int displaylinesAfter = 0;
	
	public ParseRule () {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIncludeRegex() {
		return includeRegex;
	}

	public void setIncludeRegex(String includeRegex) {
		this.includeRegex = includeRegex;
	}

	public String getExcludeRegex() {
		return excludeRegex;
	}

	public void setExcludeRegex(String excludeRegex) {
		this.excludeRegex = excludeRegex;
	}

	public int getIndent() {
		return indent;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getDisplayLinesBefore() {
		return displayLinesBefore;
	}

	public void setDisplayLinesBefore(int displayLinesBefore) {
		this.displayLinesBefore = displayLinesBefore;
	}

	public int getDisplaylinesAfter() {
		return displaylinesAfter;
	}

	public void setDisplaylinesAfter(int displaylinesAfter) {
		this.displaylinesAfter = displaylinesAfter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descritpion) {
		this.description = descritpion;
	}

	@Override
	public String toString() {
		return "ParseRule [name=" + name + ", includeRegex=" + includeRegex + ", excludeRegex=" + excludeRegex
				+ ", indent=" + indent + ", prefix=" + prefix + ", displayLinesBefore=" + displayLinesBefore
				+ ", displaylinesAfter=" + displaylinesAfter + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParseRule other = (ParseRule) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public ParseRule clone () {
		ParseRule clone = new ParseRule();
		
		clone.name = this.name;
		clone.description = this.description;
		clone.includeRegex = this.includeRegex;
		clone.excludeRegex = this.excludeRegex;
		clone.indent = this.indent;
		clone.prefix = this.prefix;
		clone.displayLinesBefore = this.displayLinesBefore;
		clone.displaylinesAfter = this.displaylinesAfter;
		
		return clone;
	}
	
	public static void copyFields (ParseRule fromRule, ParseRule toRule){
		toRule.setName(fromRule.getName());
		toRule.setDescription(fromRule.getDescription());
		toRule.setIndent(fromRule.getIndent());
		toRule.setPrefix(fromRule.getPrefix());
		toRule.setIncludeRegex(fromRule.getIncludeRegex());
		toRule.setExcludeRegex(fromRule.getExcludeRegex());
		toRule.setDisplayLinesBefore(fromRule.getDisplayLinesBefore());
		toRule.setDisplaylinesAfter(fromRule.getDisplaylinesAfter());
	}
}
