package org.jboss.reddeer.eclipse.test.ui.dialogs;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;

public class TestPropertyPage extends PropertyPage {

	public static final String PAGE_TITLE = "Property page test";

	public static final String PAGE_TEXT = "Testing page text";

	public static boolean performOkCalled = false;

	public static boolean performCancelCalled = false;

	public static boolean performApplyCalled = false;

	public static boolean performDefaultsCalled = false;

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		Label l = new Label(composite, SWT.LEFT);
		l.setText(PAGE_TEXT);
		return composite;
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
}
