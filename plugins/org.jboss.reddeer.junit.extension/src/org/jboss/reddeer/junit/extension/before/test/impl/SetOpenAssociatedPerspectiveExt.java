package org.jboss.reddeer.junit.extension.before.test.impl;

import org.eclipse.core.runtime.Platform;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.eclipse.ui.dialogs.PerspectivesPreferencePage;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;

/**
 * Extension for Extension point org.jboss.reddeer.junit.before.test. Disables
 * opening to associated perspective when project is created also dialog asking
 * to open to associated perspective is never displayed Use this system property
 * to set open associated perspective behavior:
 *
 * - reddeer.set.open.associated.perspective=[prompt|always|never]
 * (default=never)
 * 
 * @author vlado pakan
 *
 */
public class SetOpenAssociatedPerspectiveExt implements IBeforeTest {

	private static final Logger log = Logger
			.getLogger(SetOpenAssociatedPerspectiveExt.class);

	/** 
	 * See {@link IBeforeTest}.
	 */
	@Override
	public void runBeforeTest() {
		if (hasToRun()) {
			setOpenAssociatedPerspective();
		}
	}

	/** 
	 * Sets Open Associated Perspective when project is created.
	 */
	private void setOpenAssociatedPerspective() {
		String openAssociatedPerspective = Platform.getPreferencesService()
				.getString("org.eclipse.ui.ide",
						"SWITCH_PERSPECTIVE_ON_PROJECT_CREATION", "prompt",
						null);
		if (!openAssociatedPerspective
				.equalsIgnoreCase(RedDeerProperties.OPEN_ASSOCIATED_PERSPECTIVE.getValue())) {
			log.debug("Setting open associated perspective to: "
					+ RedDeerProperties.OPEN_ASSOCIATED_PERSPECTIVE.getValue()
					+ " via Windows > Preferences > General > Perspectives");

			WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
			PerspectivesPreferencePage perspectivesPreferencePage = new PerspectivesPreferencePage();
			preferencesDialog.open();
			preferencesDialog.select(perspectivesPreferencePage);
			if (RedDeerProperties.OPEN_ASSOCIATED_PERSPECTIVE
					.getValue().equalsIgnoreCase("never")) {
				perspectivesPreferencePage
						.checkNeverOpenAssociatedPerspective();
			} else if (RedDeerProperties.OPEN_ASSOCIATED_PERSPECTIVE
					.getValue().equalsIgnoreCase("always")) {
				perspectivesPreferencePage
						.checkAlwaysOpenAssociatedPerspective();
			} else if (RedDeerProperties.OPEN_ASSOCIATED_PERSPECTIVE
					.getValue().equalsIgnoreCase("prompt")) {
				perspectivesPreferencePage
						.checkPromptOpenAssociatedPerspective();
			} else {
				throw new SWTLayerException(
						"Invalid paramter value used: "
								+ "reddeer.set.open.associated.perspective = "
								+ RedDeerProperties.OPEN_ASSOCIATED_PERSPECTIVE.getValue());
			}
			preferencesDialog.ok();

		}
	}

	/** 
	 * See {@link IBeforeTest}.
	 * @return boolean
	 */
	@Override
	public boolean hasToRun() {
		return true;
	}

}
