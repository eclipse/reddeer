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
package org.eclipse.reddeer.eclipse.utils;

import static org.eclipse.reddeer.common.wait.WaitProvider.*;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.AndMatcher;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.GroupWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.swt.condition.ShellHasChildrenOrIsNotAvailable;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.AbstractShell;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;

public class DeleteUtils {
	private static final Logger log = Logger.getLogger(DeleteUtils.class);

	/**
	 * Handles specific cases during project deletion.
	 *
	 * @param deleteShell the delete shell
	 */
	public static void handleDeletion(org.eclipse.reddeer.swt.api.Shell deleteShell) {
		handleDeletion(deleteShell, TimePeriod.VERY_LONG);
	}

	/**
	 * Handles specific cases during project deletion.
	 *
	 * @param deleteShell the delete shell
	 * @param timeout            timeout
	 */
	public static void handleDeletion(org.eclipse.reddeer.swt.api.Shell deleteShell, TimePeriod timeout) {
		new WaitUntil(new ShellHasChildrenOrIsNotAvailable(deleteShell), timeout);
		int childShells = 0;
		try{
			childShells = ShellLookup.getInstance().getShells(deleteShell.getSWTWidget()).length;
		} catch (RedDeerException ex){
			log.debug("Delete shell is disposed.");
		} finally {
			TimePeriod remainingTimeout = timeout;
			if (childShells == 1) {
				org.eclipse.reddeer.swt.api.Shell s = new ShellWithButton("Delete.*", "Continue");
				new PushButton("Continue").click();
				remainingTimeout = new GroupWait(timeout, waitWhile(new ShellIsAvailable(s)), 
						waitWhile(new ShellIsAvailable(deleteShell))).getRemainingTimeout();
			}
			new WaitWhile(new JobIsRunning(), remainingTimeout);
		}
	}

	/**
	 * Deletes project via Eclipse API in case deleting via UI calls failed.
	 *
	 * @param project the project
	 * @param deleteFromFileSystem the delete from file system
	 */
	public static void forceProjectDeletion(DefaultProject project, boolean deleteFromFileSystem) {
		try {
			project.delete(deleteFromFileSystem);
		} catch (RedDeerException ele) {
			log.debug("Delete project '" + project.getName() + "' via Eclipse API ");
			org.eclipse.reddeer.direct.project.Project.delete(project.getName(), deleteFromFileSystem, true);
		}

	}

	private static class ShellWithButton extends AbstractShell {

		public ShellWithButton(String title, String buttonLabel) {
			super(lookForShellWithButton(title, buttonLabel));
			setFocus();
			log.debug("Shell with title '" + title + "' and button '" + buttonLabel + "' found");
		}

	}

	private static Shell lookForShellWithButton(final String title, final String buttonLabel) {
		Matcher<String> titleMatcher = new WithTextMatcher(new RegexMatcher(title));
		Matcher<String> buttonMatcher = new BaseMatcher<String>() {
			@Override
			public boolean matches(Object obj) {
				if (obj instanceof Control) {
					final Control control = (Control) obj;
					ReferencedComposite ref = new ReferencedComposite() {
						@Override
						public Control getControl() {
							return control;
						}
					};
					try {
						new PushButton(ref, buttonLabel);
						return true;
					} catch (CoreLayerException e) {
						// ok, this control doesn't contain the button
					}
				}
				return false;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("containing button '" + buttonLabel + "'");
			}
		};
		@SuppressWarnings("unchecked")
		Matcher<String> matcher = new AndMatcher(titleMatcher, buttonMatcher);
		return ShellLookup.getInstance().getShell(matcher);
	}

}
