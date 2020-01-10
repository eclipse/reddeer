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

public class DialogTitleIsFound extends AbstractWaitCondition {

	protected TitleAreaDialog titleAreaDialog;
	protected Matcher<?> matcher;
	
	public DialogTitleIsFound(TitleAreaDialog titleAreaDialog, String title) {
		this.titleAreaDialog = titleAreaDialog;
		this.matcher = new RegexMatcher(title);
	}
	
	public DialogTitleIsFound(TitleAreaDialog titleAreaDialog, Matcher<?> matcher) {
		this.titleAreaDialog = titleAreaDialog;
		this.matcher = matcher;
	}

	@Override
	public boolean test() {
		return matcher.matches(titleAreaDialog.getTitle());
	}

	@Override
	public String description() {
		return "dialog title match matcher " + matcher.toString();
	}	
}
