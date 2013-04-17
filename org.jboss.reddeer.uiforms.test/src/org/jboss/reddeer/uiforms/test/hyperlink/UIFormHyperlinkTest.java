package org.jboss.reddeer.uiforms.test.hyperlink;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import org.hamcrest.core.Is;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.uiforms.UIForm;
import org.jboss.reddeer.uiforms.hyperlink.UIFormHyperlink;
import org.jboss.reddeer.uiforms.section.UIFormSection;
import org.jboss.reddeer.uiforms.test.ui.views.UIFormView;
import org.jboss.reddeer.workbench.view.View;
import org.junit.Before;
import org.junit.Test;

public class UIFormHyperlinkTest extends RedDeerTest {

	private View uiFormView = new UIFormView(); 
	private UIForm uiForm;
	
	@Before
	public void openView() {
		uiFormView.open();
		uiForm = new UIForm();
	}
	
	@Test
	public void testValidInstanceParamLess() {
		uiForm.setAsReference();
		
		new UIFormSection();
		UIFormHyperlink hyperlink = new UIFormHyperlink();
		assertThat(hyperlink.getText(), Is.is("Section 1 Hyperlink"));
		
		uiForm.setAsReference();
		new UIFormSection(1);
		hyperlink = new UIFormHyperlink();
		assertThat(hyperlink.getText(), Is.is("Section 2 Hyperlink"));
	}
	
	@Test
	public void testValidInstanceWithIndex() {
		uiForm.setAsReference();
		
		new UIFormSection();
		UIFormHyperlink hyperlink = new UIFormHyperlink(0);
		assertThat(hyperlink.getText(), Is.is("Section 1 Hyperlink"));
		
		uiForm.setAsReference();
		new UIFormSection(1);
		hyperlink = new UIFormHyperlink(0);
		assertThat(hyperlink.getText(), Is.is("Section 2 Hyperlink"));
	}
	
	@Test
	public void testValidInstanceWithText() {
		uiForm.setAsReference();
		
		new UIFormSection();
		UIFormHyperlink hyperlink = new UIFormHyperlink("Section 1 Hyperlink");
		assertNotNull(hyperlink);
		
		uiForm.setAsReference();
		new UIFormSection(1);
		hyperlink = new UIFormHyperlink("Section 2 Hyperlink");
		assertNotNull(hyperlink);
	}
	
	@Test(expected=SWTLayerException.class)
	public void testInvalidInstance() {
		uiForm.setAsReference();
		
		new UIFormSection();
		new UIFormHyperlink(0);
		new UIFormHyperlink(1);
	}
	
}
