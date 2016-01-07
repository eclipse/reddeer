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
package org.jboss.reddeer.eclipse.utils;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.matcher.AndMatcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.swt.condition.ShellHasChildrenOrIsNotAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.AbstractShell;

public class DeleteUtils {
	private static final Logger log = Logger.getLogger(DeleteUtils.class);

	/**
	 * Handles specific cases during project deletion.
	 *
	 * @param deleteShell the delete shell
	 */
	public static void handleDeletion(org.jboss.reddeer.swt.api.Shell deleteShell) {
		handleDeletion(deleteShell, TimePeriod.VERY_LONG);
	}

	/**
	 * Handles specific cases during project deletion.
	 *
	 * @param deleteShell the delete shell
	 * @param timeout            timeout
	 */
	public static void handleDeletion(org.jboss.reddeer.swt.api.Shell deleteShell, TimePeriod timeout) {
		new WaitUntil(new ShellHasChildrenOrIsNotAvailable(deleteShell), timeout);
		int childShells = 0;
		try{
			childShells = ShellHandler.getInstance().getShells(deleteShell.getSWTWidget()).length;
		} catch (CoreLayerException ex){
			log.debug("Delete shell is disposed.");
		} finally {
			if (childShells == 1) {
				new ShellWithButton("Delete Resources", "Continue");
				new PushButton("Continue").click();
				new WaitWhile(new ShellWithTextIsAvailable("Delete Resources"), timeout);
			}
			new WaitWhile(new JobIsRunning(), timeout);
		}
	}

	/**
	 * Deletes project via Eclipse API in case deleting via UI calls failed.
	 *
	 * @param project the project
	 * @param deleteFromFileSystem the delete from file system
	 */
	public static void forceProjectDeletion(Project project, boolean deleteFromFileSystem) {
		try {
			project.delete(deleteFromFileSystem);
		} catch (RedDeerException ele) {
			log.debug("Delete project '" + project.getName() + "' via Eclipse API ");
			org.jboss.reddeer.direct.project.Project.delete(project.getName(), deleteFromFileSystem, true);
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
		Matcher<String> titleMatcher = new WithTextMatcher(title);
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
