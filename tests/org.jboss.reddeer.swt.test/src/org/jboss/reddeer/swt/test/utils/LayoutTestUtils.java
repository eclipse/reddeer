package org.jboss.reddeer.swt.test.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class LayoutTestUtils {

	public static Composite createFlatFormComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		FormLayout layout = new FormLayout();
		composite.setLayout(layout);
		return composite;
	}

	public static FormData entryLayoutData() {
		return entryLayoutData(null);
	}

	public static FormData entryLayoutData(Control referenceControl) {
		FormData data = new FormData();
		data = new FormData();
		data.left = new FormAttachment(0, 100);
		if (referenceControl != null) {
			data.top = new FormAttachment(referenceControl, 10);
		}
		return data;
	}

	public static FormData labelLayoutData(Control referenceControl) {
		FormData data = new FormData();
		data.right = new FormAttachment(referenceControl, -10);
		data.top = new FormAttachment(referenceControl, 0, SWT.CENTER);
		return data;
	}

}
