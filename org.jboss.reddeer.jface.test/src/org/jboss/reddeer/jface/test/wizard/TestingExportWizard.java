package org.jboss.reddeer.jface.test.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class TestingExportWizard extends Wizard implements INewWizard {

	public static final String CATEGORY = "Testing export category";
	
	public static final String NAME = "Testing export wizard";

	public TestingExportWizard() {
		setWindowTitle(NAME);
	}
	
	@Override
	public void init(IWorkbench arg0, IStructuredSelection arg1) {
	}
	
	@Override
	public void addPages() {
		addPage(new TestingNewWizardPage());
	}

	@Override
	public boolean performFinish() {
		return false;
	}
	
	public static class TestingNewWizardPage extends WizardPage {

		public static final String PAGE_NAME = "Testing export wizard page";
		
		protected TestingNewWizardPage() {
			super(PAGE_NAME);
			setTitle(PAGE_NAME);
		}

		@Override
		public void createControl(Composite parent) {
			setControl(parent);
		}
	}
}
