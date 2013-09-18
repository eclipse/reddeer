package org.jboss.reddeer.eclipse.jface.wizard;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Matches a wizard dialog with a given page index
 * 
 * @author apodhrad
 * 
 */
public class WizardPageIndex extends BaseMatcher<WizardDialog> {

	private int pageIndex;

	public WizardPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * Returns true if the wizard dialog is in a given page index
	 */
	@Override
	public boolean matches(Object item) {
		if (item instanceof WizardDialog) {
			WizardDialog wizardDialog = (WizardDialog) item;
			return wizardDialog.getPageIndex() == pageIndex;
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("with page index '" + pageIndex + "'");
	}
}
