package org.jboss.reddeer.uiforms.test.ui.views;

import org.jboss.reddeer.workbench.impl.view.WorkbenchView;


public class UIFormView extends WorkbenchView {

	private static final String TEST_CATEGORY = "Red Deer Test UI Forms";
	
	private static final String VIEW_TEXT = "UI Form Test";
	
	public UIFormView() {
		super(TEST_CATEGORY, VIEW_TEXT);
	}
}
