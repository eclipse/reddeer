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
 * @deprecated There is no need for this class any more, use 
 * {@link PreferencePage} instead 
 */
public class WorkbenchPreferencePage extends PreferencePage {

	protected final Logger log = Logger.getLogger(this.getClass());

	/**
	 * Constructor sets path to specific preference item.
	 * @param path path in preference shell tree to specific preference
	 */
	public WorkbenchPreferencePage(String... path) {
		super(path);
	}
}
