package org.jboss.reddeer.eclipse.jface.wizard;

import org.jboss.reddeer.common.logging.Logger;

/**
 * Superclass of wizard page represent single page in wizard dialog.
 * 
 * @author apodhrad
 * @since 0.5
 * @deprecated replaced by {@link org.jboss.reddeer.jface.wizard.WizardPage}
 */
public abstract class WizardPage {

	protected final Logger log = Logger.getLogger(this.getClass());

	@Deprecated
	private WizardDialog wizardDialog;
	
	@Deprecated
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

	/**
	 * Set wizard dialog for specific wizard page.
	 * 
	 * @deprecated A wizard page should not know on which page index it is displayed. The
	 * wizard page can also exist outside WizardDialog.
	 * 
	 * @param wizardDialog set wizard dialog where this wizard page belong
	 */
	@Deprecated
	public void setWizardDialog(WizardDialog wizardDialog) {
		this.wizardDialog = wizardDialog;
	}

	/**
	 * Get wizard dialog of specific wizard page
	 * 
	 * @deprecated A wizard page should not know on which page index it is displayed. The
	 * wizard page can also exist outside WizardDialog.
	 * 
	 * @return WizardDialog of specific wizard page
	 * 
	 */
	@Deprecated
	public WizardDialog getWizardDialog() {
		return wizardDialog;
	}

	/**
	 * This method in origin shows wizard page within wizard dialog. Wizard
	 * dialog has to be open before. Use methods next() or back() in
	 * WizardDialog to show the wizard page.
	 * 
	 * @Deprecated
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
	 * @deprecated This method is not implemented and is not necessary
	 * @param obj
	 */
	public void fillWizardPage(Object... obj) {
		throw new UnsupportedOperationException();
	}
}
