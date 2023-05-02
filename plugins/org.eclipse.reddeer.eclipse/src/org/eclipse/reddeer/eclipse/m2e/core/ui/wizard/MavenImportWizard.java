/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.m2e.core.ui.wizard;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.eclipse.selectionwizard.ImportMenuWizard;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.exception.SWTLayerException;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Wizard for importing an existing maven project.
 * 
 * @author apodhrad
 *
 */
public class MavenImportWizard extends ImportMenuWizard {

	public static final String TITLE = "Import Maven Projects";

	private MavenImportWizardPage page;

	/**
	 * Constructs ImportWizard with "Maven" &gt; "Existing Maven Projects".
	 */
	public MavenImportWizard() {
		super(TITLE, "Maven", "Existing Maven Projects");
		page = new MavenImportWizardPage(this);
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
		finish(timeout, true);
	}
	
	/**
	 * Finish dialog
	 * @param timeout timeout for jobs after importing maven project
	 * @param waitForResolve if finish method should wait and deal with resolve dialog
	 */
	public void finish(TimePeriod timeout, boolean waitForResolve){
		Shell shell = new DefaultShell(TITLE);
		new PushButton("Finish").click();
		new WaitWhile(new ShellIsAvailable(shell), TimePeriod.DEFAULT);
		new WaitUntil(new JobIsRunning(), TimePeriod.DEFAULT, false);
		new WaitWhile(new JobIsRunning(), timeout);
		
		if(waitForResolve){
			// check whether there some addition dialog
			try {
				ResolveDialog dialog = new ResolveDialog();
				dialog.resolveAllLater();
				try { // workaround for https://github.com/eclipse-m2e/m2e-core/issues/1371
					dialog.finish();
				} catch (org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException e) {
					dialog.cancel();
				}
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

			new WaitUntil(new JobIsRunning(), TimePeriod.DEFAULT, false);
			new WaitWhile(new JobIsRunning(), timeout);
		}
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
		
		public void cancel() {
			new PushButton("Cancel").click();
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
