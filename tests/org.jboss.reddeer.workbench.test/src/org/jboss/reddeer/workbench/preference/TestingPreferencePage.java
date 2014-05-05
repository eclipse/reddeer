package org.jboss.reddeer.workbench.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class TestingPreferencePage extends PreferencePage implements IWorkbenchPreferencePage{

	public static final String TITLE = "Testing preference page";
	
	public static boolean performOkCalled = false;
	
	public static boolean performCancelCalled = false;
	
	public static boolean performApplyCalled = false;
	
	public static boolean performDefaultsCalled = false;
	
	@Override
	protected Control createContents(Composite composite) {
		Label label = new Label(composite, SWT.LEFT);
		label.setText("Testing preference page");
		return composite;
	}

	@Override
	public void init(IWorkbench workbench) {
	}
	
	@Override
	public boolean performOk() {
		performOkCalled = true;
		return super.performOk();
	}
	
	@Override
	public boolean performCancel() {
		performCancelCalled = true;
		return super.performCancel();
	}
	
	@Override
	protected void performApply() {
		performApplyCalled = true;
		super.performApply();
	}
	
	@Override
	protected void performDefaults() {
		performDefaultsCalled = true;
		super.performDefaults();
	}
	
	public final class TestTopCategory extends PreferencePage implements IWorkbenchPreferencePage {

		public static final String TOP_CATEGORY = "Testing top category";
		
		@Override
		public void init(IWorkbench arg0) {
			
		}

		@Override
		protected Control createContents(Composite arg0) {
			return null;
		}
	}
	
	public final class TestCategory extends PreferencePage implements IWorkbenchPreferencePage {

		public static final String CATEGORY = "Testing category";
		
		@Override
		public void init(IWorkbench arg0) {
			
		}

		@Override
		protected Control createContents(Composite arg0) {
			return null;
		}
	}
}
