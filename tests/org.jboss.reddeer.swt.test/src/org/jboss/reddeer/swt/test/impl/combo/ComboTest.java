package org.jboss.reddeer.swt.test.impl.combo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.junit.After;
import org.junit.Test;
/**
 * Tests Combo functionality
 * @author Vlado Pakan
 * @author Radoslav Rabara
 */
public class ComboTest extends RedDeerTest{
	private static final String COMBO_LABEL_PREFIX = "Combo";
	private static final String COMBO_ITEM_PREFIX = "Item";
	private static final String DISABLED_COMBO_LABEL = "Disabled:";
	@Override
	public void setUp() {
		super.setUp();
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = new Shell(org.eclipse.swt.widgets.Display.getDefault());  
				shell.setText("Testing shell");
				createControls(shell);
				shell.open();
				shell.setFocus();
			}
		});
	}
	
	private void createControls(Shell shell){
		shell.setLayout(new GridLayout(2,true));
		// Text displaying last combo selection
		org.eclipse.swt.widgets.Label label = new org.eclipse.swt.widgets.Label(shell, SWT.NONE);
		label.setText("Last selection:");
		final org.eclipse.swt.widgets.Text selectionText = new org.eclipse.swt.widgets.Text(shell, SWT.BORDER|SWT.READ_ONLY);
		selectionText.setText("No selection yet");
		SelectionAdapter selectionListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				selectionText.setText(((org.eclipse.swt.widgets.Combo)e.widget).getText());
			}
		};
		for (int comboIndex = 0; comboIndex < 4; comboIndex++) {
			label = new org.eclipse.swt.widgets.Label(shell, SWT.NONE);
			label.setText(ComboTest.COMBO_LABEL_PREFIX + comboIndex);
			org.eclipse.swt.widgets.Combo combo = new org.eclipse.swt.widgets.Combo(shell, SWT.BORDER);
			combo.addSelectionListener(selectionListener);
			String[] comboItems = createSampleComboItems(comboIndex);
			for (int itemIndex = 0; itemIndex < comboItems.length; itemIndex++) {
				combo.add(comboItems[itemIndex]);
			}
		}
		label = new org.eclipse.swt.widgets.Label(shell, SWT.NONE);
		label.setText(ComboTest.DISABLED_COMBO_LABEL);
		org.eclipse.swt.widgets.Combo combo = new org.eclipse.swt.widgets.Combo(shell, SWT.BORDER);
		combo.setEnabled(false);
	}
	
	private String[] createSampleComboItems(int comboIndex) {
		final int count = 4;
		String[] items = new String[count];
		for (int i = 0; i < count; i++) {
			items[i] = ComboTest.COMBO_ITEM_PREFIX + comboIndex + i;
		}
		return items;
	}
	
	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				for (Shell shell : org.jboss.reddeer.swt.
						util.Display.getDisplay().getShells()) {
					if (shell.getText().equals("Testing shell")) {
						shell.dispose();
						break;
					}
				}
			}
		});
	}
	@Test
	public void findByIndex(){
		int index = 1;
		Combo combo = new DefaultCombo(index);
		combo.setSelection(index);
		String comboText = combo.getText();
		String expectedComboText = ComboTest.COMBO_ITEM_PREFIX + index + index;
		assertTrue("Combo text is " + comboText
				+ "\nbut expected Combo text is " + expectedComboText,
			comboText.equals(expectedComboText));
	}
	@Test
	public void findByName(){
		int index = 3;
		Combo combo = new LabeledCombo(ComboTest.COMBO_LABEL_PREFIX + index);
		combo.setSelection(index);
		String comboText = combo.getText();
		String expectedComboText = ComboTest.COMBO_ITEM_PREFIX + index + index;
		assertTrue("Combo text is " + comboText
				+ "\nbut expected Combo text is " + expectedComboText,
			comboText.equals(expectedComboText));
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingByIndex(){
		new DefaultCombo(5);
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingByLabel(){
		new LabeledCombo("NON_EXISTING_LABEL_&*");
	}
	@Test
	public void enabled(){
		Combo combo = new DefaultCombo(1);
		assertTrue("Combo is not enabled" , combo.isEnabled());
		combo = new LabeledCombo(ComboTest.DISABLED_COMBO_LABEL);
		assertFalse("Combo is enabled" , combo.isEnabled());
		
	}
	@Test
	public void selectionByIndex(){
		int comboIndex = 1;
		int itemIndex = 2;
		Combo combo = new LabeledCombo(ComboTest.COMBO_LABEL_PREFIX + comboIndex);
		combo.setSelection(itemIndex);
		String comboText = combo.getText();
		String expectedComboText = ComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex;
		assertTrue("Combo text is " + comboText
				+ "\nbut expected Combo text is " + expectedComboText,
			comboText.equals(expectedComboText));
		// Test if selection listener was invoked
		String selectionText = new DefaultText(0).getText();
		String expectedSelection = ComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex;
		assertTrue("Selection text is " + selectionText
				+ "\nbut expected selection text is " + expectedSelection,
				selectionText.equals(expectedSelection));
		
	}
	@Test
	public void selectionByItem(){
		int comboIndex = 2;
		int itemIndex = 3;
		Combo combo = new LabeledCombo(ComboTest.COMBO_LABEL_PREFIX + comboIndex);
		combo.setSelection(ComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex);
		String comboText = combo.getText();
		String expectedComboText = ComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex;
		assertTrue("Combo text is " + comboText
				+ "\nbut expected Combo text is " + expectedComboText,
			comboText.equals(expectedComboText));
		// Test if selection listener was invoked
		String selectionText = new DefaultText(0).getText();
		String expectedSelection = ComboTest.COMBO_ITEM_PREFIX + comboIndex + itemIndex;
		assertTrue("Selection text is " + selectionText
				+ "\nbut expected selection text is " + expectedSelection,
				selectionText.equals(expectedSelection));
	}
	
	@Test
	public void getAllItems() {
		for(int comboIndex = 0; comboIndex < 4; comboIndex++) {
			String[] expectedItems = createSampleComboItems(comboIndex);
			Combo[] combos = new Combo[]{
					new LabeledCombo(ComboTest.COMBO_LABEL_PREFIX + comboIndex),
					new DefaultCombo(comboIndex)
			};
			for(int i = 0; i < combos.length; i++) {
				String[] items = (String[]) combos[i].getItems().toArray();
				
				assertTrue("Retrieved items are: " + Arrays.toString(items)
						+"\nbut expected are: " + Arrays.toString(expectedItems)
						+"\nItems were retrieved from " + combos[i].getClass(),
						Arrays.equals(expectedItems, items));
			}
		}
	}
}
