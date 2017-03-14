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
package org.jboss.reddeer.core.test.condition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.test.utils.ShellTestUtils;
import org.junit.After;
import org.junit.Before;

/**
 *
 *
 * @author Jan Novak <jnovak@redhat.com>
 */
public class ShellTestBase {

	private List<DefaultShell> shells = new ArrayList<>();

	private final int TEST_SHELLS_COUNT = 5;

	protected List<DefaultShell> getTestShells() {
		return Collections.unmodifiableList(shells);
	}

	@Before
	public void openTestShells() {
		for (int i = 1; i <= TEST_SHELLS_COUNT; i++) {
			String shellTitle = "shell" + i;
			Display.syncExec(() -> {
				ShellTestUtils.createShell(shellTitle);
			});
			shells.add(new DefaultShell(shellTitle));
		}
	}

	@After
	public void closeTestShells() {
		for (DefaultShell shell : shells) {
			if (!shell.isDisposed())
				shell.close();
		}
	}

}
