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
package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import org.jboss.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.jboss.reddeer.eclipse.test.jdt.ui.AbstractResourceTest;
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
