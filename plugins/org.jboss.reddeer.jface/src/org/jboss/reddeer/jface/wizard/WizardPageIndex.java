package org.jboss.reddeer.jface.wizard;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Matches a wizard dialog with a given page index
 * 
 * @author apodhrad
 * @since 0.6
 * @deprecated since 0.8
 */
public class WizardPageIndex extends BaseMatcher<WizardDialog> {

	private int pageIndex;

	/**
	 * Constructor set page index for wizard dialog.
	 * @param pageIndex in wizard dialog
	 */
	public WizardPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}


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
