package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.jface.preference.PreferenceDialog;

/**
 * Property dialog implementation
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class PropertyDialog extends PreferenceDialog {

	/**
	 * Returns the name of the resource as will be shown on the dialog's title.
	 *
	 * @return the resource name
	 */
	protected abstract String getResourceName();
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.jface.preference.PreferenceDialog#getTitle()
	 */
	public String getTitle(){
		return "Properties for " + getResourceName();
	}
}
