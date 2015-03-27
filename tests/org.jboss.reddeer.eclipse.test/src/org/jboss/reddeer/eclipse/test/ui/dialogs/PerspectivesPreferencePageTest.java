package org.jboss.reddeer.eclipse.test.ui.dialogs;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.eclipse.ui.dialogs.PerspectivesPreferencePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class PerspectivesPreferencePageTest {

	private WorkbenchPreferenceDialog preferencesDialog = 
		new WorkbenchPreferenceDialog();

	@Test
	public void checkAllPreferences() {

		preferencesDialog.open();
		PerspectivesPreferencePage perspectivesPreferencePage = 
			new PerspectivesPreferencePage();
		preferencesDialog.select(perspectivesPreferencePage);

		perspectivesPreferencePage.checkAlwaysOpenAssociatedPerspective();
		assertTrue(perspectivesPreferencePage
				.isAlwaysOpenAssociatedPerspective());
		perspectivesPreferencePage.checkNeverOpenAssociatedPerspective();
		assertTrue(perspectivesPreferencePage
				.isNeverOpenAssociatedPerspective());
		perspectivesPreferencePage.checkOpenNewPerspectiveInNewWindow();
		assertTrue(perspectivesPreferencePage
				.isOpenNewPerspectiveInNewWindow());
		perspectivesPreferencePage.checkOpenNewPerspectiveInSameWindow();
		assertTrue(perspectivesPreferencePage
				.isOpenNewPerspectiveInSameWindow());
		perspectivesPreferencePage.checkOpenNewViewAsFastView();
		assertTrue(perspectivesPreferencePage.isOpenNewViewAsFastView());
		perspectivesPreferencePage.checkOpenNewViewWithinPerspective();
		assertTrue(perspectivesPreferencePage.isOpenNewViewWithinPerspective());
		perspectivesPreferencePage.checkPromptOpenAssociatedPerspective();
		assertTrue(perspectivesPreferencePage
				.isPromptOpenAssociatedPerspective());
		perspectivesPreferencePage.setHideEmptyFastViewBar(true);
		assertTrue(perspectivesPreferencePage.isHideEmptyFastViewBar());

		preferencesDialog.cancel();
	}

	@After
	public void tearDown() {
		// try to close preference dialog in case it stayed open
		try {
			preferencesDialog.cancel();
		} catch (SWTLayerException swtle) {
			// do nothing
		}
	}
}
