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
package org.eclipse.reddeer.codegen.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.internal.junit.util.LayoutUtil;
//import org.eclipse.jdt.internal.junit.wizards.MethodStubsSelectionButtonGroup;
//import org.eclipse.jdt.junit.wizards.NewTestCaseWizardPageOne.JUnitVersion;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.reddeer.codegen.Activator;
import org.eclipse.reddeer.codegen.builder.ClassBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;

/**
 * This class represents second RedDeer CodeGen wizard page – methods
 * specifications
 * 
 * @author djelinek
 */
@SuppressWarnings("restriction")
public class MethodsPage extends NewTypeWizardPage {

	public static final String GETTER = "Getter";

	public static final String SETTER = "Setter";

	public static final String ACTION = "Action method";

	public static final String CONSTANTS = "Generate static constants";

	public static final String INCLUDE_ALL = "Include all wizard pages";

	public static final String INHERITING = "Allow inheriting (only, if are included all wizard pages)";

	private ISelection selection;

	private List<String> selectedOption;

	private ClassBuilder classBuilder;

	private Composite composite;

	private MethodStubsSelectionButtonGroup fMethodOptionStubsButtons;

	/**
	 * Constructor for MethodsPage.
	 * 
	 * @param pageName
	 */
	public MethodsPage(ISelection selection, ClassBuilder builder) {

		super(true, "codeGenWizardPageTwo");
		setTitle("Methods setup");
		setDescription("Specify CodeGen options (aditional methods, class properties, etc.).");
		setImageDescriptor(ImageDescriptor.createFromURL(
				FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID), new Path("icons/reddeer_logo.png"), null)));
		this.selection = selection;
		this.classBuilder = builder;
		selectedOption = new ArrayList<String>();
		// Default selected options
		selectedOption.add(GETTER);
		selectedOption.add(SETTER);
		selectedOption.add(ACTION);
		selectedOption.add(CONSTANTS);
		selectedOption.add(INCLUDE_ALL);
		// selectedOption.add(INHERITING);
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {

		initializeDialogUnits(parent);
		composite = new Composite(parent, SWT.NONE);
		int nColumns = 4;

		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		
		String[] optionalButtonNames = new String[] { GETTER, SETTER, ACTION, CONSTANTS, INCLUDE_ALL, INHERITING };
		fMethodOptionStubsButtons = new MethodStubsSelectionButtonGroup(SWT.CHECK, optionalButtonNames, 1) {
			@Override
			protected void doWidgetSelected(SelectionEvent e) {
				super.doWidgetSelected(e);
				// saveWidgetValues();
				handleSelectedOptional(e);
			}
		};

		createMethodStubSelectionControls(composite, nColumns);
		// static selection of default class options
		fMethodOptionStubsButtons.setSelection(0, true);
		fMethodOptionStubsButtons.setSelection(1, true);
		fMethodOptionStubsButtons.setSelection(2, true);
		fMethodOptionStubsButtons.setSelection(3, true);
		fMethodOptionStubsButtons.setSelection(4, true);
		// fMethodOptionStubsButtons.setSelection(5, true);
		// fMethodOptionStubsButtons.setEnabled(5, false);
		setControl(composite);
		Dialog.applyDialogFont(composite);
	}

	public ClassBuilder getClassBuilder() {
		return this.classBuilder;
	}

	private void handleSelectedOptional(SelectionEvent e) {
		String name = ((Button) e.widget).getText();

		if (fMethodOptionStubsButtons.isSelected(4))
			fMethodOptionStubsButtons.setEnabled(5, true);
		else
			fMethodOptionStubsButtons.setEnabled(5, false);

		if (!selectedOption.contains(name)) {
			selectedOption.add(name);
			if (name.equals(INCLUDE_ALL) && fMethodOptionStubsButtons.isSelected(5)) {
				selectedOption.add(INHERITING);
			}
		} else {
			selectedOption.remove(name);
			if (name.equals(INCLUDE_ALL)) {
				selectedOption.remove(INHERITING);
			}
		}
	}

	protected void createMethodStubSelectionControls(Composite composite, int nColumns) {
		LayoutUtil.createEmptySpace(composite, nColumns);
		LayoutUtil.setHorizontalSpan(fMethodOptionStubsButtons.getSelectionButtonsGroup(composite), nColumns - 1);
	}

	/**
	 * Return list of selected method optional
	 * 
	 * @return List<String>
	 */
	public List<String> getSelectedOptional() {
		return selectedOption;
	}

}
