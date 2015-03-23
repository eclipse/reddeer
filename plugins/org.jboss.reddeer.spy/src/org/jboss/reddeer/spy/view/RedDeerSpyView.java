package org.jboss.reddeer.spy.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class RedDeerSpyView extends ViewPart{

	private RedDeerSpy redDeerSpy;
	
	@Override
	public void createPartControl(Composite parent) {
		redDeerSpy = new RedDeerSpy(parent);
	}

	@Override
	public void setFocus() {
	}
}
