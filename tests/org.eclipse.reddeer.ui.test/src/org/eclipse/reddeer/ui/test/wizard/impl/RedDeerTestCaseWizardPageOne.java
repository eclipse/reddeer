/*******************************************************************************
 * Copyright (c) 2017-2021 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.ui.test.wizard.impl;

import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * First page of the new RedDeer Test Case wizard
 * @author jrichter, odockal
 *
 */
public class RedDeerTestCaseWizardPageOne extends WizardPage {
	
	public RedDeerTestCaseWizardPageOne(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Sets the source folder
	 * @param folder
	 */
	public void setSourceFolder(String folder) {
		setTextToLabeledText("Source folder:", folder);
	}
	
	/**
	 * Sets the target package
	 * @param pkg
	 */
	public void setPackage(String pkg) {
		setTextToLabeledText("Package:", pkg);
	}
	
	/**
	 * Sets the test class name
	 * @param name
	 */
	public void setName(String name) {
		setTextToLabeledText("Name:", name);
	}
	
	/**
	 * Sets the superclass for the new test class
	 * @param klass
	 */
	public void setSuperClass(String klass) {
		setTextToLabeledText("Superclass:", klass);
	}
	
	/**
	 * Sets the class under test 
	 * @param klass
	 */
	public void setClassUnderTest(String klass) {
		setTextToLabeledText("Class under test:", klass);
	}
	
	/**
	 * Sets if the setUp() check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setSetupMethod(boolean checked) {
		toggleCheckBox("setUp()", "@Before setUp()", checked);
	}
	
	/**
	 * Sets if the setUpBeforeClass() check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setBeforeClassSetup(boolean checked) {
		toggleCheckBox("setUpBeforeClass()", "@BeforeClass setUpBeforeClass()", checked);
	}
	
	/**
	 * Sets if the tearDown() check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setTearDownMethod(boolean checked) {
		toggleCheckBox("tearDown()", "@After tearDown()", checked);
	}
	
	/**
	 * Sets if the tearDownAfterClass() check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setAfterClassTearDown(boolean checked) {
		toggleCheckBox("tearDownAfterClass()", "@AfterClass tearDownAfterClass()", checked);
	}
	
	/**
	 * Sets if the constructor check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setConstructor(boolean checked) {
		toggleCheckBox("constructor", "&constructor", checked);
	}
	
	/**
	 * Sets if the 'New JUnit 3 test' radio button is selected
	 * @param checked true to select, false otherwise
	 */
	public void setJUnit3(boolean select) {
		new RadioButton(this, "New JUnit 3 test").toggle(select);
	}
	
	/**
	 * Sets if the 'New JUnit 4 test' radio button is selected
	 * @param checked true to select, false otherwise
	 */
	public void setJUnit4(boolean select) {
		new RadioButton(this, "New JUnit 4 test").toggle(select);
	}
	
	/**
	 * Sets if the Generate comments check box is checked
	 * @param checked true to check, false otherwise
	 */
	public void setGenerateComments(boolean checked) {
		toggleCheckBox("Generate comments", "&Generate comments", checked);
	}
	
	private void toggleCheckBox(String text, String alternateText, boolean checked) {
		CheckBox checkBox;
		try {
			checkBox = new CheckBox(this, text);
		} catch (CoreLayerException exc) {
			checkBox = new CheckBox(this, alternateText);
		}
		checkBox.toggle(checked);
	}
	
	private void setTextToLabeledText(String label, String text) {
		new LabeledText(this, label).setText(text);
	}
}
