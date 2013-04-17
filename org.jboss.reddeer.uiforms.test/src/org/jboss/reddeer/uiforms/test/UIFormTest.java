package org.jboss.reddeer.uiforms.test;

import static org.junit.Assert.assertNotNull;

import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.uiforms.UIForm;
import org.jboss.reddeer.uiforms.test.ui.views.UIFormView;
import org.jboss.reddeer.workbench.view.View;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;
import org.junit.Before;
import org.junit.Test;

public class UIFormTest extends RedDeerTest {

	private View uiFormView = new UIFormView(); 
	private View errorLogView = new LogView();
	
	@Before
	public void openView() {
		uiFormView.open();
	}
	
	@Test
	public void testValidInstance() {
		assertNotNull(new UIForm());
	}
	
	@Test(expected=SWTLayerException.class)
	public void testInvalidInstance() {
		errorLogView.open();
		new UIForm();
	}
	
	@Test
	public void testValidReinstance() {
		new UIForm();
		uiFormView.open();
		new UIForm();
	}
	
	class LogView extends WorkbenchView {
		public LogView(){
			super("General", "Error Log");
		}
	}

}