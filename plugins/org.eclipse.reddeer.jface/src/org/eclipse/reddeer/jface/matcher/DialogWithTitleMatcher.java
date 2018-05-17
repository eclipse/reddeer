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
package org.eclipse.reddeer.jface.matcher;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.jface.dialogs.TitleAreaDialog;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;

/**
 * Matcher which matches dialogs title with given title text.
 * 
 * @author rawagner
 *
 */
public class DialogWithTitleMatcher extends BaseMatcher<String> {

	private Matcher<String> matcher;

	/**
	 * 
	 * @param title
	 *            Dialog's title
	 */
	public DialogWithTitleMatcher(String title) {
		this(new WithTextMatcher(title));
	}

	/**
	 * 
	 * @param matcher
	 *            which matches dialog's title
	 */
	public DialogWithTitleMatcher(Matcher<String> matcher) {
		this.matcher = matcher;
	}

	@Override
	public boolean matches(Object item) {
		if (item instanceof Shell) {
			if (new WindowMatcher(org.eclipse.jface.dialogs.TitleAreaDialog.class).matches(item)) {
				DefaultShell shell = new DefaultShell((Shell) item);
				TitleAreaDialog ta = new TitleAreaDialog(shell);
				return matcher.matches(ta.getTitle());
			}
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "Shell with title " + matcher;
	}

}
