/*******************************************************************************
 * Copyright (c) 2019 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.jdt.debug.ui.jres;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.stream.Collectors;

import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.jdt.debug.ui.jres.JREsPreferencePage;
import org.eclipse.reddeer.eclipse.jdt.ui.preferences.JREItem;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.property.RequirementPropertyExpandor;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests preference page for manipulation of JREs.
 * @author odockal
 *
 */
@RunWith(RedDeerSuite.class)
public class JREsPreferencePageTest {

	private WorkbenchPreferenceDialog dialog;
	private JREsPreferencePage page;
	
	@Before
	public void setup() {
		preferenceIsOpen();
	}
	
	@Test
	public void testAddingJRE() {
		addJRE(false);
		TableItem jre = getJRETableItem();
		assertFalse(jre.isChecked());
		assertFalse(page.getJREItem(getName()).isDefault());
	}
	
	@Test
	public void testAddingDefaultJRE() {
		addJRE(true);
		assertEquals(getName() + " (default)", page.getJREItem(getName()).getName());
		assertTrue(page.getJRETableItem(getName()).isChecked());
	}
	
	@Test(expected=CoreLayerException.class)
	public void testJRENotFound() {
		page.getJREItem("RandomJRE");
	}
	
	@Test(expected=EclipseLayerException.class)
	public void testOneDefaultJRE() {
		for(JREItem item : page.getJREs()) {
			page.toggleJRE(item.getName(), false);
		}
	}
	
	@After
	public void clean() {
		preferenceIsOpen();
		for (JREItem item : page.getJREs()) {
			if (item.getName().equals(getName())) {
				page.deleteJRE(getName());
			}
		}
		dialog.cancel();
	}
	
	private TableItem getJRETableItem() {
		try {
			return page.getJRETableItem(getName());
		} catch (CoreLayerException e) {
			fail("Cannot select non existing JRE: " + getName() + ". Only existing JREs are: " +
				page.getJREs().stream().map(item -> item.getName()).collect(Collectors.joining(", ")));
		}
		return null;
	}
	
	public static void addJRE(boolean setDefault) {
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		dialog.open();
		JREsPreferencePage page = new JREsPreferencePage(dialog);
		dialog.select(page);
		page.addJRE(getPath(), getName());
		if (setDefault) {
			page.setDefaultJRE(getName());
		}
	}
	
	public static String getName() {
		return "jre.test";
	}
	
	public static String getPath() {
		return RequirementPropertyExpandor.getProperty("${java.home}");
	}
	
	private void preferenceIsOpen() {
		if (dialog == null || !dialog.isOpen()) {
			dialog = new WorkbenchPreferenceDialog();
			dialog.open();
			page = new JREsPreferencePage(dialog);
			dialog.select(page);
		}		
	}
}
