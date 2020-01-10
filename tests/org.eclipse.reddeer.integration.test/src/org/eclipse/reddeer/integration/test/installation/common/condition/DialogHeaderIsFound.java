/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.integration.test.installation.common.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.jface.dialogs.TitleAreaDialog;
import org.hamcrest.Matcher;

public class DialogHeaderIsFound extends AbstractWaitCondition {

	protected TitleAreaDialog titleAreaDialog;
	protected Matcher<?> titleMatcher;
	protected Matcher<?> messageMatcher;
	
	public DialogHeaderIsFound(TitleAreaDialog titleAreaDialog, String title, String message) {
		this.titleAreaDialog = titleAreaDialog;
		this.titleMatcher = new RegexMatcher(title);
		this.messageMatcher = new RegexMatcher(message);
	}
	
	public DialogHeaderIsFound(TitleAreaDialog titleAreaDialog, Matcher<?> titleMatcher, Matcher<?> messageMatcher) {
		this.titleAreaDialog = titleAreaDialog;
		this.titleMatcher = messageMatcher;
	}

	@Override
	public boolean test() {
		return titleMatcher.matches(titleAreaDialog.getTitle()) && messageMatcher.matches(titleAreaDialog.getMessage());
	}

	@Override
	public String description() {
		return "dialog header matcher match title " + titleMatcher.toString() + " and message " + messageMatcher.toString();
	}	
}
