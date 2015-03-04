package org.jboss.reddeer.jface.preference;

import java.util.Arrays;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;

/**
 * Represents a general preference page.
 * 
 * @author Lucia Jelinkova
 * @since 0.6
 */
public abstract class PreferencePage {

	private final Logger log = Logger.getLogger(PreferencePage.class);

	private String[] path;
	
	/**
	 * Constructor sets path to specific preference item.
	 * @param path path in preference shell tree to specific preference
	 */
	public PreferencePage(String... path) {
		if (path == null) {
			throw new IllegalArgumentException("path can't be null");
		}
		if (path.length == 0) {
			throw new IllegalArgumentException("path can't be empty");
		}
		this.path = path;
	}

	/**
	 * Get name of the given preference page.
	 * 
	 * @return name of preference page
	 */
	public String getName() {
		DefaultCLabel cl = new DefaultCLabel();
		return cl.getText();
	}
	
	/**
	 * Returns path of the preference item.
	 */
	public String[] getPath() {
		return path.clone();
	}

	/**
	 * Apply preference page changes.
	 */
	public void apply() {
		Button b = new PushButton("Apply");
		log.info("Apply changes in Preferences dialog");
		b.click();
	}

	/**
	 * Restore default preference page settings.
	 */
	public void restoreDefaults() {
		Button b = new PushButton("Restore Defaults");
		log.info("Restore default values in Preferences dialog");
		b.click();
	}
	
	/**
	 * This method handles all page changes.
	 */
	public void handlePageChange(){
		
	}
	
	/**
	 * Helps to determine if PreferencePage#handlePageChange method should be called by PreferenceDialog
	 * @return title of shell which should be handled by this page
	 */
	public String getPageChangedShellName(){
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(path);
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
		PreferencePage other = (PreferencePage) obj;
		if (!Arrays.equals(path, other.path))
			return false;
		return true;
	}
	
	
}
