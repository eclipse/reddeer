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
package org.eclipse.reddeer.eclipse.test.ui.navigator;

import org.eclipse.reddeer.eclipse.test.jdt.ui.AbstractResourceTest;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.junit.Test;

public class ProjectExplorerItemTest extends AbstractResourceTest {
	
	public ProjectExplorerItemTest() {
		super(new ProjectExplorer());
	}
	
	@Test
	public void open() {
		open(PROJECT_ITEM_TEXT,DEFAULT_PACKAGE_TEXT, JAVA_CLASS_FILE_NAME);
	}
	
	@Test
	public void selectCollapsedProjectExplorerItem() {
		selectNonVisibleItem(PROJECT_ITEM_TEXT,DEFAULT_PACKAGE_TEXT, JAVA_CLASS_FILE_NAME);
	}
}
