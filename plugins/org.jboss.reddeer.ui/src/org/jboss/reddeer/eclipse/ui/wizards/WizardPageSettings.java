package org.jboss.reddeer.eclipse.ui.wizards;

/**
 * Interface to define basic wizard state methods: to set error message and
 * wizard completion.
 * 
 * @author sbunciak
 * @since 0.2
 */
public interface WizardPageSettings {

	/**
	 * @param Error message to be set 
	 */
	void setErrorMessage(String newMessage);

	/**
	 * @param Wizard page completion
	 */
	void setPageComplete(boolean complete);

}