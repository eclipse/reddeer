package org.jboss.reddeer.workbench.preference;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.jface.preference.PreferencePage;

/**
 * Represents a general preference page that is open via Window -> Preferences. Subclasses
 * should represent the concrete preference page.
 * 
 * @author Lucia Jelinkova
 * @author Radoslav Rabara
 * @since 0.6 
 */
public class WorkbenchPreferencePage extends PreferencePage {

	protected final Logger log = Logger.getLogger(this.getClass());

	private String[] path;

	/**
	 * Constructor sets path to specific preference item.
	 * @param path path in preference shell tree to specific preference
	 */
	public WorkbenchPreferencePage(String... path) {
		super();
		if (path == null) {
			throw new IllegalArgumentException("path can't be null");
		}
		if (path.length == 0) {
			throw new IllegalArgumentException("path can't be empty");
		}
		this.path = path;
	}

	/**
	 * Returns path of the preference item.
	 */
	public String[] getPath() {
		return path.clone();
	}
}
