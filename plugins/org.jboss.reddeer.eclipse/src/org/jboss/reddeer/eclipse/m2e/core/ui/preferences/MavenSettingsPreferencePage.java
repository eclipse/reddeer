package org.jboss.reddeer.eclipse.m2e.core.ui.preferences;

import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.workbench.preference.WorkbenchPreferencePage;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Class representing "Maven->User Settings" preference page.
 * 
 * @author rhopp
 *
 */

public class MavenSettingsPreferencePage extends WorkbenchPreferencePage {

	private static final String UPDATE_SETTINGS = "Update Settings";
	private static final String REINDEX = "Reindex";

	/**
	 * Construct the preference page with "Maven" > "User Settings".
	 */
	public MavenSettingsPreferencePage() {
		super("Maven", "User Settings");
	}

	/**
	 * Pushes "Update Settings" button and waits for all jobs to be finished
	 * (max. 1 minute)
	 * 
	 */

	public void updateSettings() {
		new PushButton(UPDATE_SETTINGS).click();
		new WaitUntil(new JobIsRunning(), TimePeriod.LONG);
	}

	/**
	 * Sets path to users setting.xml file
	 * 
	 * @param path Path to settings.xml file
	 */
	
	public void setUserSettingsLocation(String path) {
		getSettingsXMLTextWidget().setText(path);
	}

	/**
	 * 
	 * @return content of User Settings location
	 */
	
	public String getUserSettingsLocation() {
		return getSettingsXMLTextWidget().getText();
	}

	/**
	 * Presses "Reindex" button and waits until all jobs are finished (max. 5
	 * minutes)
	 * 
	 */

	public void reindex() {
		new PushButton(REINDEX).click();
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
	}

	
	/**
	 * Retrieves first DefaultText relative to parent of "Update Settings" push button.
	 * 
	 * @return
	 */
	
	private DefaultText getSettingsXMLTextWidget() {
		final PushButton button = new PushButton(UPDATE_SETTINGS);
		return Display.syncExec(new ResultRunnable<DefaultText>() {

			@Override
			public DefaultText run() {
				return new DefaultText(new ReferencedComposite() {

					@Override
					public Control getControl() {
						return button.getSWTWidget().getParent();
					}
				}, 0);
			}
		});
	}
}
