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
package org.eclipse.reddeer.core.test.condition.matchers;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.styledtext.DefaultStyledText;

/**
*
* @author Jan Novak <jnovak@redhat.com>
*/
public class ContainsStyledTextMatcher extends BaseMatcher<String> {

	private WithTextMatcher textMatcher;

	public ContainsStyledTextMatcher(String text) {
		super();
		textMatcher = new WithTextMatcher(text);
	}

	@Override
	public boolean matches(Object item) {
		if ((item instanceof Shell)) {
			String title = getTitle((Shell) item);
			new DefaultShell(title);
			try {
				DefaultStyledText styledText = new DefaultStyledText();
				return textMatcher.matches(styledText.getText());
			} catch (CoreLayerException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("matching by test matcher");
	}

	private String getTitle(Shell shell){
		return Display.syncExec(() ->
				shell.getText()
		);
	}
}
