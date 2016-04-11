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
package org.jboss.reddeer.swt.test.impl.button;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.SelectObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.junit.Test;

/**
 * 
 * @author jkopriva
 *
 */


public class DataBindingRadioButtonTest extends SWTLayerTestCase {

	private static final String RADIO_BUTTON_LABEL_PREFIX = "RADIO-BUTTON";

	private static final int NUM_BUTTONS = 3;

	private static final int COLUMNS = 3;
	private static final int INDENT = 40;

	private DataBindingContext dbc;

	private TestPageModel model;

	@Override
	protected void createControls(Shell shell) {
		shell.setLayout(new GridLayout(DataBindingRadioButtonTest.NUM_BUTTONS, true));

		// DBC & Model
		this.dbc = new DataBindingContext();
		this.model = new TestPageModel();

		// group
		org.eclipse.swt.widgets.Group group = new org.eclipse.swt.widgets.Group(shell, SWT.NONE);
		group.setText("Radio Group with Names");
		GridLayoutFactory.fillDefaults().applyTo(group);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(group);

		SelectObservableValue selectedRadioButtonObservable = new SelectObservableValue();

		// buttonA + labelA + textA
		org.eclipse.swt.widgets.Button buttonA = new org.eclipse.swt.widgets.Button(group, SWT.RADIO);
		buttonA.setText(RADIO_BUTTON_LABEL_PREFIX + "A");
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).span(COLUMNS, 1).applyTo(buttonA);
		selectedRadioButtonObservable.addOption("Selected: " + "A", WidgetProperties.selection().observe(buttonA));
		org.eclipse.swt.widgets.Label labelA = new org.eclipse.swt.widgets.Label(group, SWT.NONE);
		labelA.setText("Label " + "A");
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).indent(INDENT, 0).applyTo(labelA);
		final org.eclipse.swt.widgets.Text textA = new org.eclipse.swt.widgets.Text(group, SWT.BORDER);
		textA.setText("Text " + "A");
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(textA);
		
		// buttonB + labelB + textB
		org.eclipse.swt.widgets.Button buttonB = new org.eclipse.swt.widgets.Button(group, SWT.RADIO);
		buttonB.setText(RADIO_BUTTON_LABEL_PREFIX + "B");
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).span(COLUMNS, 1).applyTo(buttonB);
		selectedRadioButtonObservable.addOption("Selected: " + "B", WidgetProperties.selection().observe(buttonB));
		org.eclipse.swt.widgets.Label labelB = new org.eclipse.swt.widgets.Label(group, SWT.NONE);
		labelB.setText("Label " + "B");
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).indent(INDENT, 0).applyTo(labelB);
		org.eclipse.swt.widgets.Text textB = new org.eclipse.swt.widgets.Text(group, SWT.BORDER);
		textB.setText("Text " + "B");
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(textB);

		// buttonC + labelC + textC
		org.eclipse.swt.widgets.Button buttonC = new org.eclipse.swt.widgets.Button(group, SWT.RADIO);
		buttonC.setText(RADIO_BUTTON_LABEL_PREFIX + "C");
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).span(COLUMNS, 1).applyTo(buttonC);
		selectedRadioButtonObservable.addOption("Selected: " + "C", WidgetProperties.selection().observe(buttonC));
		org.eclipse.swt.widgets.Label labelC = new org.eclipse.swt.widgets.Label(group, SWT.NONE);
		labelC.setText("Label " + "C");
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).indent(INDENT, 0).applyTo(labelC);
		org.eclipse.swt.widgets.Text textC = new org.eclipse.swt.widgets.Text(group, SWT.BORDER);
		textC.setText("Text " + "C");
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(textC);

		// label shows selected button
		org.eclipse.swt.widgets.Label label = new org.eclipse.swt.widgets.Label(shell, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(label);
		ISWTObservableValue labelTextObservable = WidgetProperties.text().observe(label);

		// Observe
		final IObservableValue buttonAModeModelObservable = BeanProperties
				.value(TestPageModel.class, TestPageModel.BUTTON_A_BINDING_MODE).observe(model);
		final IObservableValue buttonATextModelObservable = BeanProperties
				.value(TestPageModel.class, TestPageModel.BUTTON_A_TEXT).observe(model);
		final IObservableValue buttonBModeModelObservable = BeanProperties
				.value(TestPageModel.class, TestPageModel.BUTTON_B_BINDING_MODE).observe(model);
		final IObservableValue buttonBTextModelObservable = BeanProperties
				.value(TestPageModel.class, TestPageModel.BUTTON_B_TEXT).observe(model);
		final IObservableValue buttonCModeModelObservable = BeanProperties
				.value(TestPageModel.class, TestPageModel.BUTTON_C_BINDING_MODE).observe(model);
		final IObservableValue buttonCTextModelObservable = BeanProperties
				.value(TestPageModel.class, TestPageModel.BUTTON_C_TEXT).observe(model);

		// group controls
		final Control[] buttonAControls = new Control[] { textA, labelA };
		final Control[] buttonBControls = new Control[] { textB, labelB };
		final Control[] buttonCControls = new Control[] { textC, labelC };
		buttonAModeModelObservable.addChangeListener(onButtonASelection(buttonAControls));
		buttonBModeModelObservable.addChangeListener(onButtonBSelection(buttonBControls));
		buttonCModeModelObservable.addChangeListener(onButtonCSelection(buttonCControls));

		// bindings
		dbc.bindValue(WidgetProperties.selection().observe(buttonA), buttonAModeModelObservable);
		dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textA), buttonATextModelObservable);
		dbc.bindValue(WidgetProperties.selection().observe(buttonB), buttonBModeModelObservable);
		dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textB), buttonBTextModelObservable);
		dbc.bindValue(WidgetProperties.selection().observe(buttonC), buttonCModeModelObservable);
		dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textC), buttonCTextModelObservable);
		
		//shows selected radio button
		dbc.bindValue(selectedRadioButtonObservable, labelTextObservable);

		// default settings and widgets update
		setDefaultSettings();
		updateWidgetsState(buttonAControls, buttonBControls, buttonCControls);

	}

	private void setDefaultSettings() {
		model.setButtonABindingMode(true);

	}

	private IChangeListener onButtonASelection(final Control... controls) {
		return new IChangeListener() {
			@Override
			public void handleChange(final ChangeEvent event) {
				setWidgetsEnabled(model.isButtonABindingMode(), controls);
			}
		};
	}

	private IChangeListener onButtonBSelection(final Control... controls) {
		return new IChangeListener() {
			@Override
			public void handleChange(final ChangeEvent event) {
				setWidgetsEnabled(model.isButtonBBindingMode(), controls);
			}
		};
	}

	private IChangeListener onButtonCSelection(final Control... controls) {
		return new IChangeListener() {
			@Override
			public void handleChange(final ChangeEvent event) {
				setWidgetsEnabled(model.isButtonCBindingMode(), controls);
			}
		};
	}

	private void setWidgetsEnabled(final boolean enabled, final Control... controls) {
		for (Control control : controls) {
			control.setEnabled(enabled);
		}
		// set the focus on the first element of the group.
		if (enabled) {
			for (Control control : controls) {
				if (control instanceof Text) {
					control.setFocus();
					break;
				}
			}
		}
	}

	private void updateWidgetsState(final Control[] buttonAControls, final Control[] buttonBControls,
			final Control[] buttonCControls) {
		setWidgetsEnabled(model.isButtonABindingMode(), buttonAControls);
		setWidgetsEnabled(model.isButtonBBindingMode(), buttonBControls);
		setWidgetsEnabled(model.isButtonCBindingMode(), buttonCControls);
	}

	@Test
	public void clickOnRadioButtons() {
		RadioButton radioButtonA = new RadioButton(0);
		RadioButton radioButtonB = new RadioButton(1);
		RadioButton radioButtonC = new RadioButton(2);
		
		
		LabeledText textA = new LabeledText("Label A");
		LabeledText textB = new LabeledText("Label B");
		LabeledText textC = new LabeledText("Label C");
		//radioButtonA.click();

		assertTrue("Radio Button " + radioButtonA.getText() + " is not selected", radioButtonA.isSelected());
		assertTrue("Labeled text textA is not active", textA.isEnabled());
		assertFalse("Labeled text textB is active (but it should be deactivated!)", textB.isEnabled());
		assertFalse("Labeled text textC is active (but it should be deactivated!)", textC.isEnabled());

		radioButtonB.click();

		assertTrue("Radio Button " + radioButtonB.getText() + " is not selected", radioButtonB.isSelected());
		assertFalse("Labeled text textA is active (but it should be deactivated!)", textA.isEnabled());
		assertTrue("Labeled text textB is not active", textB.isEnabled());
		assertFalse("Labeled text textC is active (but it should be deactivated!)", textC.isEnabled());

		radioButtonC.click();

		assertTrue("Radio Button " + radioButtonC.getText() + " is not selected", radioButtonC.isSelected());
		assertFalse("Labeled text textA is active (but it should be deactivated!)", textA.isEnabled());
		assertFalse("Labeled text textB is active (but it should be deactivated!)", textB.isEnabled());
		assertTrue("Labeled text textC is not active", textC.isEnabled());
	}
	

	private class TestPageModel {

		private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

		public void addPropertyChangeListener(PropertyChangeListener listener) {
			changeSupport.addPropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(PropertyChangeListener listener) {
			changeSupport.removePropertyChangeListener(listener);
		}

		public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			changeSupport.addPropertyChangeListener(propertyName, listener);
		}

		public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			changeSupport.removePropertyChangeListener(propertyName, listener);
		}

		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			changeSupport.firePropertyChange(propertyName, oldValue, newValue);
		}

		public static final String BUTTON_A_BINDING_MODE = "buttonABindingMode";
		public static final String BUTTON_B_BINDING_MODE = "buttonBBindingMode";
		public static final String BUTTON_C_BINDING_MODE = "buttonCBindingMode";

		public static final String BUTTON_A_TEXT = "buttonAText";
		public static final String BUTTON_B_TEXT = "buttonBText";
		public static final String BUTTON_C_TEXT = "buttonCText";

		private boolean buttonABindingMode = false;
		private boolean buttonBBindingMode = false;
		private boolean buttonCBindingMode = false;

		private String buttonAText = null;
		private String buttonBText = null;
		private String buttonCText = null;


		public boolean isButtonABindingMode() {
			return buttonABindingMode;
		}

		public boolean isButtonBBindingMode() {
			return buttonBBindingMode;
		}

		public boolean isButtonCBindingMode() {
			return buttonCBindingMode;
		}

		public void setButtonABindingMode(boolean buttonABindingMode) {
			firePropertyChange(BUTTON_A_BINDING_MODE, this.buttonABindingMode,
					this.buttonABindingMode = buttonABindingMode);
		}

		public void setButtonBBindingMode(boolean buttonBBindingMode) {
			firePropertyChange(BUTTON_B_BINDING_MODE, this.buttonBBindingMode,
					this.buttonBBindingMode = buttonBBindingMode);
		}

		public void setButtonCBindingMode(boolean buttonCBindingMode) {
			firePropertyChange(BUTTON_C_BINDING_MODE, this.buttonCBindingMode,
					this.buttonCBindingMode = buttonCBindingMode);
		}

		public String getButtonAText() {
			return buttonAText;
		}

		public void setButtonAText(String buttonAText) {
			firePropertyChange(BUTTON_A_TEXT, this.buttonAText, this.buttonAText = buttonAText);
		}

		public String getButtonBText() {
			return buttonBText;
		}

		public void setButtonBText(String buttonBText) {
			firePropertyChange(BUTTON_B_TEXT, this.buttonBText, this.buttonBText = buttonBText);
		}

		public String getButtonCText() {
			return buttonCText;
		}

		public void setButtonCText(String buttonCText) {
			firePropertyChange(BUTTON_C_TEXT, this.buttonCText, this.buttonCText = buttonCText);
		}

	}



}
