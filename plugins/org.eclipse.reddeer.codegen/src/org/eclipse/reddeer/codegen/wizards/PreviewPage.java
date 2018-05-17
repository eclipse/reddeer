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

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.reddeer.codegen.Activator;
import org.eclipse.reddeer.codegen.builder.ClassBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * This class represents last RedDeer CodeGen wizard page – source code preview
 * 
 * @author djelinek
 */
public class PreviewPage extends NewTypeWizardPage {

	private ISelection selection;

	private StyledText area;

	private ClassBuilder classBuilder;

	public PreviewPage(ISelection selection) {
		super(true, "codeGenWizardPageThree");
		setTitle("CodeGen preview");
		setDescription("This page shows preview of generated class. (editable)");
		setImageDescriptor(ImageDescriptor.createFromURL(
				FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID), new Path("icons/reddeer_logo.png"), null)));
		this.selection = selection;
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		composite.setLayout(layout);
		area = new StyledText(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		area.setEditable(true);
		setControl(composite);
		Dialog.applyDialogFont(composite);
	}

	/**
	 * Do refresh of text field area at preview page
	 * 
	 * @param builder
	 *            ClassBuilder
	 */
	public void updateAreaContent(ClassBuilder builder) {
		if (!area.getText().isEmpty())
			area.cut();
		area.setText(builder.toString());
	}

	/**
	 * Set new text field area content
	 * 
	 * @param str
	 *            String
	 */
	public void setAreaTXT(String str) {
		area.setText(str);
	}

	/**
	 * Return text field area content
	 * 
	 * @return String
	 */
	public String getAreaTXT() {
		return area.getText();
	}

	/**
	 * Return text field area instance
	 * 
	 * @return StyledText
	 */
	public StyledText getArea() {
		return area;
	}

	/**
	 * Return class builder instance of preview page
	 * 
	 * @return ClassBuilder
	 */
	public ClassBuilder getPreviewClassBuilder() {
		return this.classBuilder;
	}

	/**
	 * Set new content of preview page class builder instance
	 * 
	 * @param classBuilder
	 *            ClassBuilder
	 */
	public void setClassBuilder(ClassBuilder classBuilder) {
		this.classBuilder = classBuilder;
	}

}
