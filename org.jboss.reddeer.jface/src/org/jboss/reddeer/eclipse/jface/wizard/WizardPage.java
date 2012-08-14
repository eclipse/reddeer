package org.jboss.reddeer.eclipse.jface.wizard;

import org.apache.log4j.Logger;

public abstract class WizardPage {

	protected final Logger log = Logger.getLogger(this.getClass());
	private WizardDialog wizardDialog;
	private int pageIndex;
	/**
	 * Abstract class for Wizard page displayed within wizardDialog
	 * on the pageIndex page
	 * @param wizardDialog
	 * @param pageIndex
	 */
	protected WizardPage (WizardDialog wizardDialog, int pageIndex) {
	  this.wizardDialog = wizardDialog;
	  this.pageIndex = pageIndex;
	}
	/**
	 * Shows wizard page within wizard dialog
	 * Wizard dialog has to be open before
	 */
	public void show(){
	  wizardDialog.selectPage(pageIndex);
	}
	
	public WizardDialog getWizardDialog() {
		return wizardDialog;
	}	
}
