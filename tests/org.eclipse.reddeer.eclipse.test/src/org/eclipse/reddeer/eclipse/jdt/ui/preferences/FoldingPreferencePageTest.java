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
package org.eclipse.reddeer.eclipse.jdt.ui.preferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class FoldingPreferencePageTest {

	private static FoldingPreferencePage page;

	private WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
	
	@Before
	public void setUp() {
		dialog.open();
		page = new FoldingPreferencePage(dialog);
		dialog.select(page);
	}

	@After
	public void tearDown() {
		dialog.cancel();
	}

	@Test
	public void testFolding() {
		page.disableFolding();
		assertFalse(page.isFoldingChecked());
		page.enableFolding();
		assertTrue(page.isFoldingChecked());
	}

	@Test
	public void testComments() {
		page.enableComments();
		assertTrue(page.isCommentsChecked());
		page.disableComments();
		assertFalse(page.isCommentsChecked());

	}

	@Test
	public void testHeaderComments() {
		page.disableHeaderComments();
		assertFalse(page.isHeaderCommentsChecked());
		page.enableHeaderComments();
		assertTrue(page.isHeaderCommentsChecked());
	}

	@Test
	public void testInnerTypes() {
		page.enableInnerTypes();
		assertTrue(page.isInnerTypesChecked());
		page.disableInnerTypes();
		assertFalse(page.isInnerTypesChecked());
	}

	@Test
	public void testMembers() {
		page.enableMembers();
		assertTrue(page.isMembersChecked());
		page.disableMembers();
		assertFalse(page.isMembersChecked());
	}

	@Test
	public void testImports() {
		page.enableImports();
		assertTrue(page.isImportsChecked());
		page.disableImports();
		assertFalse(page.isImportsChecked());
	}

}

