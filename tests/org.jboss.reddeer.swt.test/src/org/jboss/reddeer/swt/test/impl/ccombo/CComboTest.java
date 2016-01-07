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
package org.jboss.reddeer.swt.test.impl.ccombo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.swt.api.CCombo;
import org.jboss.reddeer.swt.impl.ccombo.DefaultCCombo;
import org.jboss.reddeer.swt.impl.ccombo.LabeledCCombo;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.jboss.reddeer.swt.test.utils.LabelTestUtils;
import org.jboss.reddeer.swt.test.utils.TextTestUtils;
import org.junit.Test;

/**
 * Tests CCombo functionality
 * 
 * @author Andrej Podhradsky
 * 
 */
public class CComboTest extends SWTLayerTestCase {

	private static final String COMBO_LABEL_PREFIX = "Combo";
	private static final String COMBO_ITEM_PREFIX = "Item";
	private static final String DISABLED_COMBO_LABEL = "Disabled:";

	@Override
	protected void createControls(Shell shell) {
		// Text displaying last combo selection
		LabelTestUtils.createLabel(shell, "Last selection:");
		final Text selectionText = TextTestUtils.createText(shell, "No selection yet");

		SelectionAdapter selectionListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectionText.setText(((org.eclipse.swt.custom.CCombo) e.widget).getText());
			}
		};
		for (int comboIndex = 0; comboIndex < 4; comboIndex++) {
			LabelTestUtils.createLabel(shell, COMBO_LABEL_PREFIX + comboIndex);
			createCCombo(shell, selectionListener, comboIndex);
		}

		LabelTestUtils.createLabel(shell, DISABLED_COMBO_LABEL);
		org.eclipse.swt.custom.CCombo combo = new org.eclipse.swt.custom.CCombo(shell, SWT.BORDER);
		combo.setEnabled(false);
	}

	private void createCCombo(Shell shell, SelectionAdapter selectionListener, int comboIndex) {
		org.eclipse.swt.custom.CCombo combo = new org.eclipse.swt.custom.CCombo(shell, SWT.BORDER);
		combo.addSelectionListener(selectionListener);
		String[] comboItems = createSampleComboItems(comboIndex);
		for (int itemIndex = 0; itemIndex < comboItems.length; itemIndex++) {
			combo.add(comboItems[itemIndex]);
		}
	}

	private String[] createSampleComboItems(int comboIndex) {
		final int count = 4;
		String[] items = new String[count];
		for (int i = 0; i < count; i++) {
			items[i] = CComboTest.COMBO_ITEM_PREFIX + comboIndex + i;
		}
		return items;
	}

	@Test
	public void findByIndex() {
		int index = 1;
		CCombo ccombo = new DefaultCCombo(index);
		ccombo.setSelection(index);
		String ccomboText = ccombo.getText();
		String expectedCComboText = CComboTest.COMBO_ITEM_PREFIX + index + index;
		assertTrue("CCombo text is " + ccomboText + "\nbut expected CCombo text is " + expectedCComboText,
				ccomboText.equals(expectedCComboText));
	}

	@Test
	public void findByName() {
		int index = 3;
		CCombo ccombo = new LabeledCCombo(CComboTest.COMBO_LABEL_PREFIX + index);
		ccombo.setSelection(index);
		String ccomboText = ccombo.getText();
		String expectedCComboText = CComboTest.COMBO_ITEM_PREFIX + index + index;
		assertTrue("CCombo text is " + ccomboText + "\nbut expected CCombo text is " + expectedCComboText,
				ccomboText.equals(expectedCComboText));
	}

	@Test(expected = CoreLayerException.class)
	public void findNonExistingByIndex() {
		new DefaultCCombo(5);
	}

	@Test(expected = CoreLayerException.class)
	public void findNonExistingByLabel() {
		new LabeledCCombo("NON_EXISTING_LABEL_&*");
	}

	@Test
	public void enabled() {
		CCombo ccombo = new DefaultCCombo(1);
		assertTrue("CCombo is not enabled", ccombo.isEnabled());
		ccombo = new LabeledCCombo(CComboTest.DISABLED_COMBO_LABEL);
		assertFalse("CCombo is enabled", ccombo.isEnabled());

	}

	@Test
	public void selectionByIndex() {
		int comboIndex = 1;
		int itemIndex = 2;
		CCombo ccombo = new LabeledCCombo(CComboTest.COMBO_LABEL_PREFIX + comboIndex);
		ccombo.setSelection(itemIndex);
		String ccomboText = ccombo.getText();
		String expectedCComboText = CComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex;
		assertTrue("CCombo text is " + ccomboText + "\nbut expected CCombo text is " + expectedCComboText,
				ccomboText.equals(expectedCComboText));
		// Test if selection listener was invoked
		String selectionText = new DefaultText(0).getText();
		String expectedSelection = CComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex;
		assertTrue("Selection text is " + selectionText + "\nbut expected selection text is " + expectedSelection,
				selectionText.equals(expectedSelection));

	}

	@Test
	public void selectionByItem() {
		int ccomboIndex = 2;
		int itemIndex = 3;
		CCombo ccombo = new LabeledCCombo(CComboTest.COMBO_LABEL_PREFIX + ccomboIndex);
		ccombo.setSelection(CComboTest.COMBO_ITEM_PREFIX + ccomboIndex + itemIndex);
		String ccomboText = ccombo.getText();
		String expectedCComboText = CComboTest.COMBO_ITEM_PREFIX + ccomboIndex + itemIndex;
		assertTrue("Combo text is " + ccomboText + "\nbut expected Combo text is " + expectedCComboText,
				ccomboText.equals(expectedCComboText));
		// Test if selection listener was invoked
		String selectionText = new DefaultText(0).getText();
		String expectedSelection = CComboTest.COMBO_ITEM_PREFIX + ccomboIndex + itemIndex;
		assertTrue("Selection text is " + selectionText + "\nbut expected selection text is " + expectedSelection,
				selectionText.equals(expectedSelection));
	}

	@Test
	public void getAllItems() {
		for (int ccomboIndex = 0; ccomboIndex < 4; ccomboIndex++) {
			String[] expectedItems = createSampleComboItems(ccomboIndex);
			CCombo[] ccombos = new CCombo[] { new LabeledCCombo(CComboTest.COMBO_LABEL_PREFIX + ccomboIndex),
					new DefaultCCombo(ccomboIndex) };
			for (int i = 0; i < ccombos.length; i++) {
				String[] items = (String[]) ccombos[i].getItems().toArray();

				assertTrue("Retrieved items are: " + Arrays.toString(items) + "\nbut expected are: "
						+ Arrays.toString(expectedItems) + "\nItems were retrieved from " + ccombos[i].getClass(),
						Arrays.equals(expectedItems, items));
			}
		}
	}
}
