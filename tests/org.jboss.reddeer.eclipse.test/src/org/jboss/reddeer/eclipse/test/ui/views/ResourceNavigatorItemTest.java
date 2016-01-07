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
package org.jboss.reddeer.eclipse.test.ui.views;

import org.jboss.reddeer.eclipse.test.jdt.ui.AbstractExplorerItemTest;
import org.jboss.reddeer.eclipse.ui.views.navigator.ResourceNavigator;
import org.junit.Test;

public class ResourceNavigatorItemTest extends AbstractExplorerItemTest{

	public ResourceNavigatorItemTest() {
		super(new ResourceNavigator());
	}
	
	@Test
	public void open() {
		open(PROJECT_ITEM_TEXT,	JAVA_CLASS_FILE_NAME);
	}
	
	@Test
	public void getChild() {
		getChild(PROJECT_ITEM_TEXT);
	}
	
	@Test
	public void getChildren() {
		getChildren(PROJECT_ITEM_TEXT);
	}
	
	@Test
	public void selectCollapsedResourceNavigatorItem() {
		selectNonVisibleItem(PROJECT_ITEM_TEXT,	JAVA_CLASS_FILE_NAME);
	}
}
