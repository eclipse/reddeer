package org.jboss.reddeer.uiforms.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.uiforms.impl.form.DefaultForm;
import org.jboss.reddeer.uiforms.test.ui.views.FormView;
import org.jboss.reddeer.uiforms.test.ui.views.UIFormView;
import org.jboss.reddeer.workbench.api.View;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultFormTest {

	private View uiFormView = new UIFormView(); 
	
	private View errorLogView = new LogView();
	
	@Before
	public void openView() {
		uiFormView.open();
	}
	
	@Test
	public void defaultConsturctor() {
		assertThat(new DefaultForm().getText(), is(FormView.FORM_A_TITLE));
	}
	
	@Test
	public void indexedConsturctor() {
		assertThat(new DefaultForm(1).getText(), is(FormView.FORM_B_TITLE));
	}
	
	@Test
	public void titleConsturctor() {
		assertThat(new DefaultForm(FormView.FORM_B_TITLE).getText(), is(FormView.FORM_B_TITLE));
	}
	
	@Test
	public void defaultConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultForm(rc).getText(), is(FormView.FORM_C_TITLE));
	}
	
	@Test
	public void indexedConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultForm(rc, 1).getText(), is(FormView.FORM_D_TITLE));
	}
	
	@Test
	public void titleConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultForm(rc, FormView.FORM_D_TITLE).getText(), is(FormView.FORM_D_TITLE));
	}
	
	@Test(expected=SWTLayerException.class)
	public void testInvalidInstance() {
		errorLogView.open();
		new DefaultForm();
	}
	
	class LogView extends WorkbenchView {
		public LogView(){
			super("General", "Error Log");
		}
	}

}