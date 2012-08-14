package org.jboss.reddeer.workbench.test.ui.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class LabelView extends ViewPart {
	private Label label;

	public LabelView() {
		super();
	}

	public void setFocus() {
		label.setFocus();
	}

	public void createPartControl(Composite parent) {
		label = new Label(parent, 0);
		label.setText("Hello World");
	}

}
