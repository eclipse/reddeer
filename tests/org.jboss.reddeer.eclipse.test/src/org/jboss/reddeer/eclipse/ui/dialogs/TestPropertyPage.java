package org.jboss.reddeer.eclipse.ui.dialogs;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;

public class TestPropertyPage extends PropertyPage {

	public static final String PAGE_TITLE = "Property page test page";
	
	public static final String PAGE_TEXT = "Testing page text";
	
	@Override
	protected Control createContents(Composite parent) {
		Label l = new Label(parent, SWT.LEFT);
		l.setText(PAGE_TEXT);
		return parent;
	}
}
