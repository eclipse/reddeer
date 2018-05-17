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
package org.eclipse.reddeer.workbench.test.ui.views;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.part.ViewPart;

/**
 * A saveable view which contains one labeled text field. Once you edit the text field, the view is set as dirty and
 * after saving it is set as non-dirty. The view is opened as non-dirty by default. This behavior can be changed by
 * setting the system property {@link DirtyLabelView#DEFAULT_DIRTY_PROPERTY}.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class DirtyLabelView extends ViewPart implements ISaveablePart {

	public static final String DEFAULT_DIRTY_PROPERTY = "rd.viewTest.isDirty";
	public static final boolean DEFAULT_DIRTY_VALUE = false;

	private static String lastSavedText = "";

	private Label label;
	private Text text;
	private boolean isDirty;

	public DirtyLabelView() {
		super();
		setDirty(getDefaultDirtyValue());
	}

	public void setFocus() {
		label.setFocus();
	}

	public void createPartControl(Composite parent) {
		GridLayout gridLayout = new GridLayout(2, true);
		gridLayout.verticalSpacing = 8;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(gridLayout);

		label = new Label(composite, SWT.NONE);
		label.setText("Test field: ");
		text = new Text(composite, SWT.SINGLE | SWT.BORDER);
		text.setText(lastSavedText);
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent event) {
				if (!lastSavedText.equals(text.getText())) {
					setDirty(true);
				}
			}
		});
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		lastSavedText = text.getText();
		setDirty(false);
	}

	@Override
	public void doSaveAs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public boolean isSaveOnCloseNeeded() {
		return false;
	}

	private boolean getDefaultDirtyValue() {
		return Boolean.valueOf(System.getProperty(DEFAULT_DIRTY_PROPERTY, String.valueOf(DEFAULT_DIRTY_VALUE)));
	}

}
