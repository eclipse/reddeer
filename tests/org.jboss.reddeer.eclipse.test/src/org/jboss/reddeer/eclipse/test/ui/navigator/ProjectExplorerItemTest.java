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
package org.jboss.reddeer.eclipse.test.ui.navigator;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.test.jdt.ui.AbstractExplorerItemTest;
import org.junit.Ignore;
import org.junit.Test;

public class ProjectExplorerItemTest extends AbstractExplorerItemTest {
	
	public ProjectExplorerItemTest() {
		super(new ProjectExplorer());
	}
	
	@Test
	public void open() {
		open(PROJECT_ITEM_TEXT,DEFAULT_PACKAGE_TEXT, JAVA_CLASS_FILE_NAME);
	}
	
	@Test
	public void getChild() {
		getChild(PROJECT_ITEM_TEXT, DEFAULT_PACKAGE_TEXT);
	}
	
	@Ignore("Ignored due to Eclipse issue https://bugs.eclipse.org/bugs/show_bug.cgi?id=447880")
	@Test
	public void getChildren() {
		getChildren(PROJECT_ITEM_TEXT, DEFAULT_PACKAGE_TEXT);
	}
	
	@Test
	public void selectCollapsedProjectExplorerItem() {
		selectNonVisibleItem(PROJECT_ITEM_TEXT,DEFAULT_PACKAGE_TEXT, JAVA_CLASS_FILE_NAME);
	}
}
