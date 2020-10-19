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
package org.eclipse.reddeer.eclipse.test.m2e.core.ui.preferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;

import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.eclipse.m2e.core.ui.preferences.MavenArchetypesPreferencePage;
import org.eclipse.reddeer.eclipse.test.Activator;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MavenArchetypesPreferencePageTest {

	private static final String LOCAL_CATALOG_DESCRIPTION = "Local Test Catalog";
	private static final String REMOTE_CATALOG_FILE = "https://repo1.maven.org/maven2/archetype-catalog.xml";
	private static final String REMOTE_CATALOG_DESCRIPTION = "Remote Test Catalog";
	private static final String EMPTY_CATALOG_MESSAGE = "Remote catalog is empty";

	private WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
	private MavenArchetypesPreferencePage page = new MavenArchetypesPreferencePage(preferencesDialog);

	private File path = new File(Activator.getTestResourcesLocation(MavenArchetypesPreferencePageTest.class),
			"archetype-catalog.xml");

	@After
	public void tearDown() {
		preferencesDialog.cancel();
	}

	@Test
	public void setLocalCatalogTest() {
		preferencesDialog.open();
		preferencesDialog.select(page);
		page.addLocalCatalog(path.getAbsolutePath(), LOCAL_CATALOG_DESCRIPTION);
		assertEquals(true, page.containsCatalog("Local: " + LOCAL_CATALOG_DESCRIPTION));
		page.removeCatalog("Local: " + LOCAL_CATALOG_DESCRIPTION);
		assertEquals(false, page.containsCatalog("Local: " + LOCAL_CATALOG_DESCRIPTION));
	}

	@Test
	public void editLocalCatalogTest() {
		String localCatalogName = "Local: " + LOCAL_CATALOG_DESCRIPTION;
		preferencesDialog.open();
		preferencesDialog.select(page);
		page.addLocalCatalog(path.getAbsolutePath(), LOCAL_CATALOG_DESCRIPTION);
		assertEquals(true, page.containsCatalog(localCatalogName));
		localCatalogName = localCatalogName + " test";
		page.editLocalCatalog(localCatalogName, path.getAbsolutePath(), localCatalogName);
		page.removeCatalog(localCatalogName);
		assertEquals(false, page.containsCatalog(localCatalogName));
	}

	@Test
	public void setRemoteCatalogTest() {
		preferencesDialog.open();
		preferencesDialog.select(page);
		assertNotEquals(EMPTY_CATALOG_MESSAGE, page.addRemoteCatalog(REMOTE_CATALOG_FILE, REMOTE_CATALOG_DESCRIPTION));
		assertEquals(true, page.containsCatalog("Remote: " + REMOTE_CATALOG_DESCRIPTION));
		page.removeCatalog("Remote: " + REMOTE_CATALOG_DESCRIPTION);
		assertEquals(false, page.containsCatalog("Remote: " + REMOTE_CATALOG_DESCRIPTION));
	}

	@Test
	public void editRemoteCatalogTest() {
		String remoteCatalogName = "Remote: " + REMOTE_CATALOG_DESCRIPTION;
		preferencesDialog.open();
		preferencesDialog.select(page);
		assertNotEquals(EMPTY_CATALOG_MESSAGE, page.addRemoteCatalog(REMOTE_CATALOG_FILE, REMOTE_CATALOG_DESCRIPTION));
		assertEquals(true, page.containsCatalog(remoteCatalogName));
		remoteCatalogName = remoteCatalogName + " test";
		assertNotEquals(EMPTY_CATALOG_MESSAGE,
				page.editRemoteCatalog(remoteCatalogName, REMOTE_CATALOG_FILE, remoteCatalogName));
		assertEquals(true, page.containsCatalog("Remote: " + remoteCatalogName));
		page.removeCatalog(remoteCatalogName);
		assertEquals(false, page.containsCatalog(remoteCatalogName));
	}

	@Test
	public void setRemoteCatalogNotVerifyTest() {
		preferencesDialog.open();
		preferencesDialog.select(page);
		assertEquals("", page.addRemoteCatalog(REMOTE_CATALOG_FILE + "_test", REMOTE_CATALOG_DESCRIPTION, false));
		assertEquals(true, page.containsCatalog("Remote: " + REMOTE_CATALOG_DESCRIPTION));
		page.removeCatalog("Remote: " + REMOTE_CATALOG_DESCRIPTION);
		assertEquals(false, page.containsCatalog("Remote: " + REMOTE_CATALOG_DESCRIPTION));
	}

	@Test
	public void editRemoteCatalogNotVerifyTest() {
		String remoteCatalogName = "Remote: " + REMOTE_CATALOG_DESCRIPTION;
		preferencesDialog.open();
		preferencesDialog.select(page);
		page.addRemoteCatalog(REMOTE_CATALOG_FILE, REMOTE_CATALOG_DESCRIPTION);
		assertEquals(true, page.containsCatalog(remoteCatalogName));
		remoteCatalogName = remoteCatalogName + " test";
		assertEquals("",
				page.editRemoteCatalog(remoteCatalogName, REMOTE_CATALOG_FILE + "abc", remoteCatalogName, false));
		assertEquals(true, page.containsCatalog("Remote: " + remoteCatalogName));
		page.removeCatalog(remoteCatalogName);
		assertEquals(false, page.containsCatalog(remoteCatalogName));
	}

}
