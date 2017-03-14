/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.reddeer.core.test.condition.matchers;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;

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
