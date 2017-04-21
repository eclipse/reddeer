/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.core.test.condition;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.reddeer.common.condition.WaitCondition;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.core.condition.ShellMatchingMatcherIsAvailable;
import org.eclipse.reddeer.core.test.condition.matchers.ContainsStyledTextMatcher;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Jan Novak <jnovak@redhat.com>
 */
@RunWith(RedDeerSuite.class)
public class ShellMatchingMatcherIsAvailableTest extends ShellTestBase {

	Map<DefaultShell, WaitCondition> conditions = new HashMap<>();
	List<WaitCondition> expectedAvailable = new ArrayList<>();
	List<WaitCondition> expectedNotAvailable = new ArrayList<>();

	@Before
	public void prepareTestedConditions() {
		for (DefaultShell shell : getTestShells()) {
			String expectedContent = getExpectedContent(shell);
			WaitCondition available
					= new ShellMatchingMatcherIsAvailable(new ContainsStyledTextMatcher(expectedContent));

			conditions.put(shell, available);
		}
		expectedNotAvailable.addAll(conditions.values());
	}
	
	@Test
	public void testShellContainsStyledText() {
		assertAvailable(expectedAvailable);
		assertNotAvailable(expectedNotAvailable);

		for (DefaultShell shell : getTestShells()) {
			insertStyledText(shell);

			WaitCondition condition = conditions.get(shell);
			expectedNotAvailable.remove(condition);
			expectedAvailable.add(condition);

			assertAvailable(expectedAvailable);
			assertNotAvailable(expectedNotAvailable);
		}
	}

	private String getExpectedContent(DefaultShell shell) {
		return shell.getText() + " Styled text";
	}

	private void assertAvailable(List<WaitCondition> conditions) {
		for (WaitCondition condition : conditions) {
			assertTrue(condition.test());
		}
	}

	private void assertNotAvailable(List<WaitCondition> conditions) {
		for (WaitCondition condition : conditions) {
			assertFalse(condition.test());
		}
	}

	private void insertStyledText(DefaultShell shell) {
		String textContent = getExpectedContent(shell);
		Display.syncExec(() -> {
				shell.getSWTWidget().setLayout(new GridLayout());
				StyledText styledText = new StyledText(shell.getSWTWidget(), SWT.MULTI);
				styledText.setText(textContent);
				GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
				data.heightHint = 100;
				styledText.setLayoutData(data);
				shell.getSWTWidget().layout();
		});
	}

}
