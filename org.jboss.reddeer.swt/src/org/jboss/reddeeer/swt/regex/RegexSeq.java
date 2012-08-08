package org.jboss.reddeeer.swt.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Regex list of paterns for widgets usage
 * @author Jiri Peterka
 *
 */
public class RegexSeq {

	List<Pattern> patterns; 
	
	public  RegexSeq(String ... regexes) {
		patterns = new ArrayList<Pattern>();
		
		for (String regex : regexes) {
			Pattern p = Pattern.compile(regex);
			patterns.add(p);
		}
	}	
	
	public List<Pattern> getPatterns() {
		return patterns;
	}
	
}
