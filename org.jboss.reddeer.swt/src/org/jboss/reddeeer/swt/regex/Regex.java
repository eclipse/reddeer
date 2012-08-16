package org.jboss.reddeeer.swt.regex;

import java.util.regex.Pattern;

/**
 * Regex pattern class for widgets usage
 * @author Jiri Peterka
 *
 */
public class Regex {

	private Pattern pattern;
	
	public  Regex(String regex) {
			pattern = Pattern.compile(regex);
	}	
	
	public Pattern getPattern() {
		return pattern;
	}
	
}
