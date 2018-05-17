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
package org.eclipse.reddeer.eclipse.test.jdt.ui.packageexplorer;

import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.test.jdt.ui.AbstractResourceTest;
import org.junit.Test;

public class PackageExplorerItemTest extends AbstractResourceTest {

	public PackageExplorerItemTest() {
		super(new PackageExplorerPart());
	}
	
	@Test
	public void open() {
		open(PROJECT_ITEM_TEXT,DEFAULT_PACKAGE_TEXT, JAVA_CLASS_FILE_NAME);
	}
	
	@Test
	public void selectCollapsedPackageExplorerItem() {
		selectNonVisibleItem(PROJECT_ITEM_TEXT,DEFAULT_PACKAGE_TEXT, JAVA_CLASS_FILE_NAME);
	}
}
