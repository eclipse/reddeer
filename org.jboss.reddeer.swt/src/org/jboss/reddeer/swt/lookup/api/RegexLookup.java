package org.jboss.reddeer.swt.lookup.api;



/**
 * API for Regex lookup
 * Usage: Shell s = Shells.byRegex<Shell>("Eclipse.*");
 * @author Jiri Peterka
 *
 */
public interface RegexLookup<A> {
	

	/**
	 * Return widget by given regex
	 */
	A byRegex(String regex);
		
}
