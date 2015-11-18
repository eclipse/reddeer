package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.jface.preference.PreferencePage;

/**
 * Represents a general property page.
 * 
 * @author Lucia Jelinkova
 * 
 */
public abstract class PropertyPage extends PreferencePage {

	/**
	 * Instantiates a new property page.
	 *
	 * @param path the path
	 */
	public PropertyPage(String... path) {
		super(path);
	}
}
