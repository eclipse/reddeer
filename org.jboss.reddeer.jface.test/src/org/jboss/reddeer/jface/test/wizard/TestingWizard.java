package org.jboss.reddeer.jface.test.wizard;

import java.util.Properties;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jboss.reddeer.swt.util.Display;

public class TestingWizard extends Wizard {

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

	private static class TestingPage extends org.eclipse.jface.wizard.WizardPage {

		private String pageName;
		private Properties properties;
		private org.eclipse.swt.widgets.Text text;
		private org.eclipse.swt.widgets.Label label;

		protected TestingPage(String pageName, Properties properties) {
			super(pageName);
			this.pageName = pageName;
			this.properties = properties;
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
	}
}
