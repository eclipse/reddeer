package org.jboss.reddeer.uiforms.test.section;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import org.hamcrest.core.Is;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.uiforms.UIForm;
import org.jboss.reddeer.uiforms.section.UIFormSection;
import org.jboss.reddeer.uiforms.test.ui.views.UIFormView;
import org.jboss.reddeer.workbench.view.View;
import org.junit.Before;
import org.junit.Test;

public class UIFormSectionTest extends RedDeerTest {

	private View uiFormView = new UIFormView(); 
	
	@Before
	public void openView() {
		uiFormView.open();
		new UIForm();
	}
	
	@Test
	public void testValidInstanceParamLess() {
		UIFormSection section = new UIFormSection();
		assertNotNull(section);
		assertThat(section.getText(), Is.is("Section 1"));
	}
	
	@Test
	public void testValidInstanceWithIndex() {
		UIFormSection section = new UIFormSection(0);
		assertNotNull(section);
		assertThat(section.getText(), Is.is("Section 1"));
	}
	
	@Test
	public void testValidInstanceWithText() {
		UIFormSection section = new UIFormSection("Section 1");
		assertNotNull(section);
		assertThat(section.getText(), Is.is("Section 1"));
	}
	
	@Test(expected=SWTLayerException.class)
	public void testInvalidInstance() {
		UIFormSection section = new UIFormSection();
		new UIFormSection(section, "Section 2");
	}
	
	@Test
	public void testValidReinstance() {
		new UIFormSection("Section 1");
		new UIFormSection("Section 2");
	}
	
}
