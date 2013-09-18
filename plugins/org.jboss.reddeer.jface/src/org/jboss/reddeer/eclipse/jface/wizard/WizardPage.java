package org.jboss.reddeer.eclipse.jface.wizard;

import org.apache.log4j.Logger;

/**
 * Wizard page
 * 
 * @author apodhrad
 * 
 */
public abstract class WizardPage {

	protected final Logger log = Logger.getLogger(this.getClass());

	private WizardDialog wizardDialog;
	private int pageIndex;

	/**
	 * A wizard page should not know on which page index it is displayed. The
	 * wizard page can also exist outside WizardDialog. Use no-argument
	 * constructor instead.
	 * 
	 * @param wizardDialog
	 * @param pageIndex
	 */
	@Deprecated
	protected WizardPage(WizardDialog wizardDialog, int pageIndex) {
		this.wizardDialog = wizardDialog;
		this.pageIndex = pageIndex;
	}

	protected WizardPage() {

	}

	public void setWizardDialog(WizardDialog wizardDialog) {
		this.wizardDialog = wizardDialog;
	}

	public WizardDialog getWizardDialog() {
		return wizardDialog;
	}

	/**
	 * This method in origin shows wizard page within wizard dialog. Wizard
	 * dialog has to be open before. Use methods next() or back() in
	 * WizardDialog to show the wizard page.
	 */
	@Deprecated
	public void show() {
		if (wizardDialog == null) {
			throw new IllegalStateException("WizardDialog was not initialized!");
		}
		wizardDialog.selectPage(pageIndex);
	}

	/**
	 * Fills the wizard page if implemented
	 * 
	 * @param obj
	 */
	public void fillWizardPage(Object... obj) {
		throw new UnsupportedOperationException();
	}
}
