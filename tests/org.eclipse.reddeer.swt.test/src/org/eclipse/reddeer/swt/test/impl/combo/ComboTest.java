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
package org.eclipse.reddeer.swt.test.impl.combo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.WithIdMatcher;
import org.eclipse.reddeer.swt.api.Combo;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.test.SWTLayerTestCase;
import org.eclipse.reddeer.swt.test.utils.LabelTestUtils;
import org.eclipse.reddeer.swt.test.utils.TextTestUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.Test;

/**
 * Tests Combo functionality
 * 
 * @author Vlado Pakan
 * @author Radoslav Rabara
 */
public class ComboTest extends SWTLayerTestCase {
	private static final String COMBO_TEST_KEY = "comboTestKey";
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
				selectionText.setText(((org.eclipse.swt.widgets.Combo) e.widget).getText());
			}
		};
		for (int comboIndex = 0; comboIndex < 4; comboIndex++) {
			LabelTestUtils.createLabel(shell, COMBO_LABEL_PREFIX + comboIndex);
			createCombo(shell, selectionListener, comboIndex);
		}

		LabelTestUtils.createLabel(shell, DISABLED_COMBO_LABEL);
		org.eclipse.swt.widgets.Combo combo = new org.eclipse.swt.widgets.Combo(shell, SWT.BORDER);
		combo.setEnabled(false);
	}

	private void createCombo(Shell shell, SelectionAdapter selectionListener, int comboIndex) {
		org.eclipse.swt.widgets.Combo combo = new org.eclipse.swt.widgets.Combo(shell, SWT.BORDER);
		combo.addSelectionListener(selectionListener);
		String[] comboItems = createSampleComboItems(comboIndex);
		for (int itemIndex = 0; itemIndex < comboItems.length; itemIndex++) {
			combo.add(comboItems[itemIndex]);
		}
		combo.setData(COMBO_TEST_KEY, comboIndex);
	}

	private String[] createSampleComboItems(int comboIndex) {
		final int count = 4;
		String[] items = new String[count];
		for (int i = 0; i < count; i++) {
			items[i] = ComboTest.COMBO_ITEM_PREFIX + comboIndex + i;
		}
		return items;
	}

	@Test
	public void findByIndex() {
		int index = 1;
		Combo combo = new DefaultCombo(index);
		combo.setSelection(index);
		String comboText = combo.getText();
		String expectedComboText = ComboTest.COMBO_ITEM_PREFIX + index + index;
		assertTrue("Combo text is " + comboText + "\nbut expected Combo text is " + expectedComboText,
				comboText.equals(expectedComboText));
	}

	@Test
	public void findByName() {
		int index = 3;
		Combo combo = new LabeledCombo(ComboTest.COMBO_LABEL_PREFIX + index);
		combo.setSelection(index);
		String comboText = combo.getText();
		String expectedComboText = ComboTest.COMBO_ITEM_PREFIX + index + index;
		assertTrue("Combo text is " + comboText + "\nbut expected Combo text is " + expectedComboText,
				comboText.equals(expectedComboText));
	}

	@Test
	public void findById() {
		int index = 3;
		Combo combo = new DefaultCombo(new WithIdMatcher(COMBO_TEST_KEY, index));
		combo.setSelection(index);
		String comboText = combo.getText();
		String expectedComboText = ComboTest.COMBO_ITEM_PREFIX + index + index;
		assertTrue("Combo text is " + comboText + "\nbut expected Combo text is " + expectedComboText,
				comboText.equals(expectedComboText));
	}

	@Test(expected = CoreLayerException.class)
	public void findNonExistingByIndex() {
		new DefaultCombo(5);
	}

	@Test(expected = CoreLayerException.class)
	public void findNonExistingByLabel() {
		new LabeledCombo("NON_EXISTING_LABEL_&*");
	}

	@Test
	public void enabled() {
		Combo combo = new DefaultCombo(1);
		assertTrue("Combo is not enabled", combo.isEnabled());
		combo = new LabeledCombo(ComboTest.DISABLED_COMBO_LABEL);
		assertFalse("Combo is enabled", combo.isEnabled());

	}

	@Test
	public void selectionByIndex() {
		int comboIndex = 1;
		int itemIndex = 2;
		Combo combo = new LabeledCombo(ComboTest.COMBO_LABEL_PREFIX + comboIndex);
		combo.setSelection(itemIndex);
		String comboText = combo.getText();
		String expectedComboText = ComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex;
		assertTrue("Combo text is " + comboText + "\nbut expected Combo text is " + expectedComboText,
				comboText.equals(expectedComboText));
		// Test if selection listener was invoked
		String selectionText = new DefaultText(0).getText();
		String expectedSelection = ComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex;
		assertTrue("Selection text is " + selectionText + "\nbut expected selection text is " + expectedSelection,
				selectionText.equals(expectedSelection));

	}

	@Test
	public void selectionByItem() {
		int comboIndex = 2;
		int itemIndex = 3;
		Combo combo = new LabeledCombo(ComboTest.COMBO_LABEL_PREFIX + comboIndex);
		combo.setSelection(ComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex);
		String comboText = combo.getText();
		String expectedComboText = ComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex;
		assertTrue("Combo text is " + comboText + "\nbut expected Combo text is " + expectedComboText,
				comboText.equals(expectedComboText));
		// Test if selection listener was invoked
		String selectionText = new DefaultText(0).getText();
		String expectedSelection = ComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex;
		assertTrue("Selection text is " + selectionText + "\nbut expected selection text is " + expectedSelection,
				selectionText.equals(expectedSelection));
	}

	@Test
	public void getAllItems() {
		for (int comboIndex = 0; comboIndex < 4; comboIndex++) {
			String[] expectedItems = createSampleComboItems(comboIndex);
			Combo[] combos = new Combo[] {
				new LabeledCombo(ComboTest.COMBO_LABEL_PREFIX + comboIndex),
				new DefaultCombo(comboIndex) };
			for (int i = 0; i < combos.length; i++) {
				String[] items = (String[]) combos[i].getItems().toArray();

				assertTrue("Retrieved items are: " + Arrays.toString(items) + "\nbut expected are: "
						+ Arrays.toString(expectedItems) + "\nItems were retrieved from " + combos[i].getClass(),
						Arrays.equals(expectedItems, items));
			}
		}
	}
}
