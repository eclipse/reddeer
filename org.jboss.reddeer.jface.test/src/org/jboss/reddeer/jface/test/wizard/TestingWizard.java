package org.jboss.reddeer.jface.test.wizard;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jboss.reddeer.swt.util.Display;

public class TestingWizard extends Wizard {

	private boolean finishEnabled = true;
	
	public TestingWizard() {
	}

	@Override
	public void addPages() {
		addPage(new TestingPage("A"));
		addPage(new TestingPage("B"));
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

		protected TestingPage(String pageName) {
			super(pageName);
			this.pageName = pageName;
		}

		@Override
		public void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);
			GridLayout gl = new GridLayout();
			gl.numColumns = 1;
			composite.setLayout(gl);	
			
			org.eclipse.swt.custom.CLabel id = new org.eclipse.swt.custom.CLabel(composite, SWT.LEFT);
			id.setText(pageName);
			setControl(composite);
		}
	}
}

