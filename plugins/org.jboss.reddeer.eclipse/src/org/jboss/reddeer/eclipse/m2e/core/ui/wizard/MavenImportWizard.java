/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.eclipse.m2e.core.ui.wizard;

import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.jface.wizard.ImportWizardDialog;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Wizard for importing an existing maven project.
 * 
 * @author apodhrad
 *
 */
public class MavenImportWizard extends ImportWizardDialog {

	public static final String TITLE = "Import Maven Projects";

	private MavenImportWizardPage page;

	/**
	 * Constructs ImportWizard with "Maven > Existing Maven Projects".
	 */
	public MavenImportWizard() {
		super("Maven", "Existing Maven Projects");
		page = new MavenImportWizardPage();
	}

	public MavenImportWizardPage getWizardPage() {
		return page;
	}

	@Override
	public void finish() {
		finish(TimePeriod.VERY_LONG);
	}

	@Override
	public void finish(TimePeriod timeout) {
		Shell shell = new DefaultShell(TITLE);
		new PushButton("Finish").click();
		new WaitWhile(new ShellIsAvailable(shell), TimePeriod.NORMAL);
		new WaitUntil(new JobIsRunning(), TimePeriod.NORMAL, false);
		new WaitWhile(new JobIsRunning(), timeout);

		// check whether there some addition dialog
		try {
			ResolveDialog dialog = new ResolveDialog();
			dialog.resolveAllLater();
			dialog.finish();
		} catch (Exception e) {
			// ok, it means that the warning wasn't displayed
		}
		// check whether there some addition dialog
		try {
			IncompleteDialog dialog = new IncompleteDialog();
			dialog.ok();
		} catch (CoreLayerException | SWTLayerException e) {
			// ok, it means that the warning wasn't displayed
		}

		new WaitUntil(new JobIsRunning(), TimePeriod.NORMAL, false);
		new WaitWhile(new JobIsRunning(), timeout);
	}

	/**
	 * Imports the whole project.
	 * 
	 * @param path
	 *            Path
	 */
	public static void importProject(String path) {
		importProject(path, TimePeriod.VERY_LONG);
	}

	/**
	 * Imports the whole project.
	 * 
	 * @param path
	 *            Path
	 * @param timeout
	 *            Timeout
	 */
	public static void importProject(String path, TimePeriod timeout) {
		MavenImportWizard wizard = new MavenImportWizard();
		wizard.open();
		wizard.getWizardPage().setRootDirectory(path);
		wizard.getWizardPage().refresh();
		wizard.getWizardPage().waitUntilProjectIsLoaded(TimePeriod.VERY_LONG);
		wizard.finish(timeout);
	}

	/**
	 * Additional dialog which occurs only during importing a maven project.
	 */
	private class ResolveDialog extends DefaultShell {

		public static final String TITLE_MARS = "Import Maven Projects";
		public static final String TITLE_NEON = "Discover m2e connectors";

		public ResolveDialog() {
			super(new WithTextMatcher(new RegexMatcher("(" + TITLE_MARS + ")|(" + TITLE_NEON + ")")));
		}

		public void resolveAllLater() {
			new PushButton("Resolve All Later").click();
		}

		public void finish() {
			new PushButton("Finish").click();
			new WaitWhile(new ShellIsAvailable(this));
		}

	}

	/**
	 * Additional dialog which occurs only during importing a maven project.
	 */
	private class IncompleteDialog extends DefaultShell {

		public IncompleteDialog() {
			super("Incomplete Maven Goal Execution");
		}

		public void ok() {
			new PushButton("OK").click();
			new WaitWhile(new ShellIsAvailable(this));
		}

	}
}
