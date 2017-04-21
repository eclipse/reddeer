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
package org.eclipse.reddeer.eclipse.test.ui.views;

import org.eclipse.reddeer.eclipse.test.jdt.ui.AbstractResourceTest;
import org.eclipse.reddeer.eclipse.ui.views.navigator.ResourceNavigator;
import org.junit.Test;

public class ResourceNavigatorItemTest extends AbstractResourceTest{

	public ResourceNavigatorItemTest() {
		super(new ResourceNavigator());
	}
	
	@Test
	public void open() {
		open(PROJECT_ITEM_TEXT,	JAVA_CLASS_FILE_NAME);
	}
	
	@Test
	public void selectCollapsedResourceNavigatorItem() {
		selectNonVisibleItem(PROJECT_ITEM_TEXT,	JAVA_CLASS_FILE_NAME);
	}
}
