/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.jface.test.wizard;

import java.util.Properties;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.reddeer.common.util.Display;

public class TestingWizard extends Wizard {

	public static final String TITLE = "Testing Wizard";
	public static final String PAGE_TITLE = "Testing Page Title";
	public static final String PAGE_DESCRIPTION = "Testing Page Description";
	
	private boolean finishEnabled = true;
	private Properties properties;
	
	public TestingWizard() {
		properties = new Properties();
	}

	@Override
	public void addPages() {
		addPage(new TestingPage("A", properties));
		addPage(new TestingPage("B", properties));
	}

	@Override
	public boolean performFinish() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean canFinish() {
		return finishEnabled;
	}

	public void setFinishEnabled(boolean finishEnabled) {
		this.finishEnabled = finishEnabled;
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				getContainer().updateButtons();
			}
		});
	}
	@Override
	public String getWindowTitle(){
		return TestingWizard.TITLE;
	}
	
	private static class TestingPage extends org.eclipse.jface.wizard.WizardPage {

		private String pageName;
		private Properties properties;
		private org.eclipse.swt.widgets.Text text;
		private org.eclipse.swt.widgets.Label label;

		protected TestingPage(String pageName, Properties properties) {
			super(pageName);
			this.pageName = pageName;
			this.properties = properties;
			setTitle(TestingWizard.PAGE_TITLE);
			setDescription(TestingWizard.PAGE_DESCRIPTION);
		}
		
		@Override
		public void setVisible(boolean visible) {
			super.setVisible(visible);
			String name = properties.getProperty("name");
			if (name != null) { 
				label.setText("Age:");
			}
		}

		@Override
		public void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);
			GridLayout gl = new GridLayout();
			gl.numColumns = 2;
			composite.setLayout(gl);

			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan=2;org.eclipse.swt.custom.CLabel id = new org.eclipse.swt.custom.CLabel(composite, SWT.LEFT);
			id.setText(pageName);
			id.setLayoutData(gd);
			label = new org.eclipse.swt.widgets.Label(composite, SWT.NULL);
			label.setText("Name:");
			text = new org.eclipse.swt.widgets.Text(composite, SWT.BORDER | SWT.SINGLE);
			text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			setControl(composite);
		}

		@Override
		public IWizardPage getNextPage() {
			if (text != null && text.getText().length() > 0) {
				properties.put("name", text.getText());
			}
			return super.getNextPage();
		}
		
		@Override
		public IWizardPage getPreviousPage() {
			if (text != null && text.getText().length() > 0) {
				properties.put("name", text.getText());
			}
			return super.getPreviousPage();
		}
	}
}
