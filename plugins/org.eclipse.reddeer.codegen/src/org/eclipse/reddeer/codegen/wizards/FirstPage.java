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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.reddeer.codegen.Activator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * This class represents first RedDeer CodeGen wizard page – basic class
 * informations
 * 
 * @author djelinek
 */
public class FirstPage extends NewTypeWizardPage {

	private final static String PAGE_NAME = "codeGenWizardPageOne";

	private ISelection selection;

	private MethodsPage methodsPage;

	private IJavaElement jelem;

	/**
	 * Constructor for FirstWizardPage.
	 * 
	 * @param pageName
	 */
	public FirstPage(ISelection selection, MethodsPage methodsPage) {
		super(true, PAGE_NAME);
		this.setTitle("Class definition");
		this.setDescription("This wizard allows generate a new CodeGen class.");
		this.setImageDescriptor(ImageDescriptor.createFromURL(
				FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID), new Path("icons/reddeer_logo.png"), null)));
		this.selection = selection;
		this.methodsPage = methodsPage;
	}

	/**
	 * The wizard owning this page is responsible for calling this method with the
	 * current selection. The selection is used to initialize the fields of the
	 * wizard page.
	 *
	 * @param selection
	 *            used to initialize the fields
	 */
	public void init(IStructuredSelection selection) {
		jelem = getInitialJavaElement(selection);
	}

	private void doStatusUpdate() {
		// all used component status
		IStatus[] status = new IStatus[] { fContainerStatus,
				isEnclosingTypeSelected() ? fEnclosingTypeStatus : fPackageStatus,
				// fTypeNameStatus,
				fModifierStatus, fSuperInterfacesStatus };

		// the mode severe status will be displayed and the OK button
		// enabled/disabled.
		updateStatus(status);
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	@Override
	public void createControl(Composite parent) {

		initializeDialogUnits(parent);
		Composite composite = new Composite(parent, SWT.NONE);
		int nColumns = 4;

		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		createContainerControls(composite, nColumns);
		createPackageControls(composite, nColumns);
		createSeparator(composite, nColumns);
		createTypeNameControls(composite, nColumns);
		setControl(composite);

		// initialization of FirstPage fields
		initContainerPage(jelem);
		initTypePage(jelem);
		// setTypeName("DefaultCodeGen", true);

		// message field status updates
		// updateStatus(containerChanged());
		// updateStatus(packageChanged());
		// updateStatus(typeNameChanged());
		doStatusUpdate();
		dialogChanged();
		Dialog.applyDialogFont(composite);
		setPageComplete(false);
	}

	@Override
	public boolean canFlipToNextPage() {
		if (!getPackageFragmentRootText().isEmpty() && !getPackageText().isEmpty() && !getTypeName().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void handleFieldChanged(String fieldName) {

		if (fieldName.equals(CONTAINER)) {
			// updateStatus(containerChanged());
		} else if (fieldName.equals(TYPENAME)) {
			methodsPage.getClassBuilder().setClassName(getTypeName());
			// dialogChanged();
			// updateStatus(typeNameChanged());
		} else if (fieldName.equals(PACKAGE)) {
			methodsPage.getClassBuilder().setPackage(getPackageText());
			// updateStatus(packageChanged());
		}
		// doStatusUpdate();
		dialogChanged();
		getContainer().updateButtons();
		super.handleFieldChanged(fieldName);
	}

	/**
	 * Ensures that both text fields are set.
	 */
	public void dialogChanged() {

		String fileName = getTypeName();
		updateStatus(containerChanged());
		updateStatus(packageChanged());
		// updateStatus(typeNameChanged());
		if (fileName.length() == 0) {
			updateStatusMsg("Class name must be specified.");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatusMsg("Class name must be valid.");
			return;
		}
		if (fileName.indexOf('.') != -1) {
			updateStatusMsg("Class name must not be qualified.");
			return;
		}
		IPackageFragment pack = getPackageFragment();
		if (pack != null) {
			ICompilationUnit cu = pack.getCompilationUnit(getCompilationUnitName(fileName));
			IResource resource = cu.getResource();
			if (resource.exists()) {
				setMessage("This class name already exists.", SWT.ICON_INFORMATION);
				setPageComplete(false);
				return;
			}
		}
		// updateStatusMsg(null);
		doStatusUpdate();
	}

	/**
	 * Update page status message row
	 * 
	 * @param message
	 *            String
	 */
	private void updateStatusMsg(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

}
