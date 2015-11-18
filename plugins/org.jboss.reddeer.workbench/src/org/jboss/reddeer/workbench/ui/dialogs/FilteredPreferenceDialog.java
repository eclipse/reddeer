package org.jboss.reddeer.workbench.ui.dialogs;

import static org.hamcrest.MatcherAssert.assertThat;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.jface.preference.PreferenceDialog;
/**
 * Filtered Preference Dialog implementation that has to bve opened
 * before it's methods are called
 *
 * @author Vlado Pakan
 * @since 1.0
 */
public class FilteredPreferenceDialog extends PreferenceDialog {

	public static final String DIALOG_TITLE = "Preferences (Filtered)";

	private final Logger log = Logger.getLogger(FilteredPreferenceDialog.class);

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.jface.preference.PreferenceDialog#getTitle()
	 */
	@Override
	public String getTitle() {
		return DIALOG_TITLE;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.jface.preference.PreferenceDialog#openImpl()
	 */
	@Override
	protected void openImpl() {
		log.info("Open Filtered Preferences");
		// actually only activates already opened dialog
		assertThat("Filetered preferences dialog has to opened already", isOpen());
	}
}
