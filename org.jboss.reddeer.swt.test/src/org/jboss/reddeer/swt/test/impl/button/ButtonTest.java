package org.jboss.reddeer.swt.test.impl.button;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.ArrowButton;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.button.ToggleButton;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.junit.After;
import org.junit.Test;

public class ButtonTest extends RedDeerTest{
	private static final String PUSH_BUTTON_LABEL_PREFIX = "PUSH-BUTTON";
	private static final String RADIO_BUTTON_LABEL_PREFIX = "RADIO-BUTTON";
	private static final String TOGGLE_BUTTON_LABEL_PREFIX = "TOOGLE-BUTTON";
	private static final String CHECK_BOX_LABEL_PREFIX = "CHECK-BOX";
	private static final String ARROW_BUTTON_LABEL_PREFIX = "ARROW-BUTTON";
	private static final String TOOLTIP_PREFIX = "TOOLTIP FOR: ";
	private static final int NUM_BUTTONS = 3;
	private Text selectionText = null;
	private SelectionListener selectionListener;
	@Override
	public void setUp() {
		super.setUp();
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = new Shell( Display.getDisplay());
				shell.setText("Testing shell");
				createControls(shell);
				shell.open();
				shell.setFocus();
			}
		});
	}
	
	private void createControls(Shell shell){
		shell.setLayout(new GridLayout(ButtonTest.NUM_BUTTONS,true));
		this.selectionListener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				org.eclipse.swt.widgets.Button selectedButton = (org.eclipse.swt.widgets.Button)arg0.widget;
				getSelectionText().setText(selectedButton.getData().toString());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
			}
		};
		createButtons(ButtonTest.PUSH_BUTTON_LABEL_PREFIX, SWT.PUSH, shell);
		createButtons(ButtonTest.TOGGLE_BUTTON_LABEL_PREFIX, SWT.TOGGLE, shell);
		createButtons(ButtonTest.RADIO_BUTTON_LABEL_PREFIX, SWT.RADIO, shell);
		createButtons(ButtonTest.CHECK_BOX_LABEL_PREFIX, SWT.CHECK, shell);
		createButtons(ButtonTest.ARROW_BUTTON_LABEL_PREFIX, SWT.ARROW, shell);
		org.eclipse.swt.widgets.Text txSelection = new org.eclipse.swt.widgets.Text(shell,SWT.BORDER);
		txSelection.setText("<text of selected button>");
	}
	private void createButtons(String textPrefix , int style , Shell shell){
		for (int i = 0; i < ButtonTest.NUM_BUTTONS; i++) {
			org.eclipse.swt.widgets.Button button = new org.eclipse.swt.widgets.Button(shell,style);
			button.setText(textPrefix + i);
			button.setToolTipText(ButtonTest.TOOLTIP_PREFIX + textPrefix + i);
			button.setData(textPrefix + i);
			button.addSelectionListener(selectionListener);
			if (style == SWT.RADIO && i == 0){
				button.setSelection(true);
			}
		}
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
	/**
	 * Finds Push Button by index and check getText(), getToolTipText() and click() methods
	 */
	@Test
	public void findPushButtonByIndexAndCheck(){
		int index = 2;
		checkButton(ButtonTest.PUSH_BUTTON_LABEL_PREFIX + index, new PushButton(index));
	}
	/**
	 * Finds Radio Button by index and check getText(), getToolTipText() and click() methods
	 */
	@Test
	public void findRadioButtonByIndexAndCheck(){
		int index = 1;
		RadioButton radioButton = new RadioButton(index);
		checkButton(ButtonTest.RADIO_BUTTON_LABEL_PREFIX + index, radioButton);
		assertTrue("Radio Button " + radioButton.getText() + " is not selected", 
			radioButton.isSelected());
		radioButton = new RadioButton(0);
		assertFalse("Radio Button " + radioButton.getText() + " is selected", 
			radioButton.isSelected());
		radioButton = new RadioButton(2);
		assertFalse("Radio Button " + radioButton.getText() + " is selected", 
			radioButton.isSelected());
	}
	/**
	 * Finds Toggle Button by index and check getText(), getToolTipText() and click() methods
	 */
	@Test
	public void findToggleButtonByIndexAndCheck(){
		int index = 2;
		ToggleButton toggleButton = new ToggleButton(index);
		checkButton(ButtonTest.TOGGLE_BUTTON_LABEL_PREFIX + index, toggleButton);
		assertTrue("Toggle Button " + toggleButton.getText() + " is not selected", 
				toggleButton.isSelected());
	}
	/**
	 * Finds Check Box by index and check getText(), getToolTipText() and click() methods
	 */
	@Test
	public void findCheckBoxByIndexAndCheck(){
		int index = 1;
		CheckBox checkBox = new CheckBox(index);
		checkButton(ButtonTest.CHECK_BOX_LABEL_PREFIX + index, checkBox);
		assertTrue("Check Box " + checkBox.getText() + " is not selected", 
				checkBox.isChecked());

	}
	/**
	 * Finds Arrow Button by index and check getText(), getToolTipText() and click() methods
	 */
	@Test
	public void findArrowButtonByIndexAndCheck(){
		int index = 2;
		checkButton(ButtonTest.ARROW_BUTTON_LABEL_PREFIX + index, new ArrowButton(index));
	}
	/**
	 * Finds Push Button by label and check getText(), getToolTipText() and click() methods
	 */
	@Test
	public void findPushButtonByLabelAndCheck(){
		String label = ButtonTest.PUSH_BUTTON_LABEL_PREFIX + 0;
		checkButton(label, new PushButton(label));
	}
	/**
	 * Finds Radio Button by label and check getText(), getToolTipText() and click() methods
	 */
	@Test
	public void findRadioButtonByLabelAndCheck(){
		String label = ButtonTest.RADIO_BUTTON_LABEL_PREFIX + 1;
		checkButton(label, new RadioButton(label));
	}
	/**
	 * Finds Toggle Button by label and check getText(), getToolTipText() and click() methods
	 */
	@Test
	public void findToggleButtonByLabelAndCheck(){
		String label = ButtonTest.TOGGLE_BUTTON_LABEL_PREFIX + 2;
		checkButton(label, new ToggleButton(label));
	}
	/**
	 * Finds Check Box by label and check getText(), getToolTipText() and click() methods
	 */
	@Test
	public void findCheckBoxByLabelAndCheck(){
		String label = ButtonTest.CHECK_BOX_LABEL_PREFIX + 1;
		checkButton(label, new CheckBox(label));
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingPushButtonByIndex(){
		new PushButton(ButtonTest.NUM_BUTTONS + 2);
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingRadioButtonByIndex(){
		new RadioButton(ButtonTest.NUM_BUTTONS + 1);
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingArrowButtonByIndex(){
		new ArrowButton(ButtonTest.NUM_BUTTONS + 3);
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingCheckBoxByIndex(){
		new CheckBox(ButtonTest.NUM_BUTTONS + 2);
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingToggleButtonByIndex(){
		new ToggleButton(ButtonTest.NUM_BUTTONS + 1);
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingPushButtonByLabel(){
		new PushButton("NON_EXISTING_#$SDFF@S");
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingRadioButtonByLabel(){
		new RadioButton("NON_EXISTING_@QWEDSA@");
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingCheckBoxByLabel(){
		new CheckBox("NON_EXISTING_DASLKJ");
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingToggleButtonByLabel(){
		new ToggleButton("NON_EXISTING_KL2FG");
	}
	private Text getSelectionText(){
		if (this.selectionText == null){
			this.selectionText = new DefaultText(0);
		}
		return this.selectionText;
	}
	@Test
	public void toggleRadioButton(){
		int index = 1;
		RadioButton radioButton = new RadioButton(index);
		if (radioButton.isSelected()){
			new RadioButton(index - 1).click();
		}
		assertTrue("Radio Button " + radioButton.getText() + " is selected", 
			!radioButton.isSelected());
		radioButton.toggle(true);
		assertTrue("Radio Button " + radioButton.getText() + " is not selected", 
			radioButton.isSelected());
		// check selection listener invocation. It should be invoked by toggle() method
		assertTrue("Selection Listener was not invoked",
			getSelectionText().getText().equals(ButtonTest.RADIO_BUTTON_LABEL_PREFIX + index));
		// Invoke click on Push Button to set selection text to other button text
		int pushButtonIndex = 0;
		new PushButton(0).click();
		assertTrue("Selection Listener was not invoked",
				getSelectionText().getText().equals(ButtonTest.PUSH_BUTTON_LABEL_PREFIX + pushButtonIndex));
		radioButton.toggle(true);
		// check selection listener invocation. It should not be invoked by toggle() method
		assertTrue("Selection Listener was invoked",
			getSelectionText().getText().equals(ButtonTest.PUSH_BUTTON_LABEL_PREFIX + pushButtonIndex));
	}
	@Test
	public void toggleCheckBox(){
		int index = 1;
		CheckBox checkBox = new CheckBox(index);
		if (checkBox.isChecked()){
			checkBox.click();	
		}
		assertTrue("Check Box " + checkBox.getText() + " is checked", 
				!checkBox.isChecked());
		checkBox.toggle(true);
		assertTrue("Check Box " + checkBox.getText() + " is not checked", 
				checkBox.isChecked());
		// check selection listener invocation. It should be invoked by toggle() method
		assertTrue("Selection Listener was not invoked",
			getSelectionText().getText().equals(ButtonTest.CHECK_BOX_LABEL_PREFIX + index));
		// Invoke click on Push Button to set selection text to other button text
		int pushButtonIndex = 0;
		new PushButton(0).click();
		assertTrue("Selection Listener was not invoked",
				getSelectionText().getText().equals(ButtonTest.PUSH_BUTTON_LABEL_PREFIX + pushButtonIndex));
		checkBox.toggle(true);
		// check selection listener invocation. It should not be invoked by toggle() method
		assertTrue("Selection Listener was invoked",
			getSelectionText().getText().equals(ButtonTest.PUSH_BUTTON_LABEL_PREFIX + pushButtonIndex));
	}
	@Test
	public void toggleToggleButton(){
		int index = 1;
		ToggleButton toggleButton = new ToggleButton(index);
		if (toggleButton.isSelected()){
			toggleButton.click();	
		}
		assertTrue("Toggle Button " + toggleButton.getText() + " is checked", 
			!toggleButton.isSelected());
		toggleButton.toggle(true);
		assertTrue("Toggle Button " + toggleButton.getText() + " is not checked", 
			toggleButton.isSelected());
		// check selection listener invocation. It should be invoked by toggle() method
		assertTrue("Selection Listener was not invoked",
			getSelectionText().getText().equals(ButtonTest.TOGGLE_BUTTON_LABEL_PREFIX + index));
				// Invoke click on Push Button to set selection text to other button text
		// Invoke click on Push Button to set selection text to other button text
		int pushButtonIndex = 0;
		new PushButton(0).click();
		assertTrue("Selection Listener was not invoked",
				getSelectionText().getText().equals(ButtonTest.PUSH_BUTTON_LABEL_PREFIX + pushButtonIndex));
		toggleButton.toggle(true);
		// check selection listener invocation. It should not be invoked by toggle() method
		assertTrue("Selection Listener was invoked",
			getSelectionText().getText().equals(ButtonTest.PUSH_BUTTON_LABEL_PREFIX + pushButtonIndex));
	}
	/**
	 * Checks tooltip text and selection listener invocation for button
	 * @param expectedText
	 * @param button
	 */
	private void checkButton (String expectedText, Button button){
		// check button text except for ARROW Button
		if (!expectedText.startsWith(ButtonTest.ARROW_BUTTON_LABEL_PREFIX)){
			assertTrue("Expected button text is " + expectedText
					+ "\nbut Button text is " + button.getText(),
				button.getText().equals(expectedText));
		}
		final String expectedToolTipText = ButtonTest.TOOLTIP_PREFIX + expectedText;
		assertTrue("Expected button tooltip text is " + expectedToolTipText
				+ "\nbut Button tool tip text is " + button.getToolTipText(),
			button.getToolTipText().equals(expectedToolTipText));
		button.click();
		// check selection listener invocation
		assertTrue("Selection Listener was not invoked",
			getSelectionText().getText().equals(expectedText));
	}
}
