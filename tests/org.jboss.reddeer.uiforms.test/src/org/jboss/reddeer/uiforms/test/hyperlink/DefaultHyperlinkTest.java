package org.jboss.reddeer.uiforms.test.hyperlink;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.uiforms.api.Hyperlink;
import org.jboss.reddeer.uiforms.impl.form.DefaultForm;
import org.jboss.reddeer.uiforms.impl.hyperlink.DefaultHyperlink;
import org.jboss.reddeer.uiforms.test.ui.views.FormView;
import org.jboss.reddeer.uiforms.test.ui.views.UIFormView;
import org.jboss.reddeer.workbench.api.View;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultHyperlinkTest {

	private View uiFormView = new UIFormView(); 
	
	@Before
	public void openView() {
		uiFormView.open();
	}
	
	@Test
	public void defaultConsturctor() {
		assertThat(new DefaultHyperlink().getText(), is(FormView.HYPERLINK_PREFIX + FormView.SECTION_A));
	}
	
	@Test
	public void indexedConsturctor() {
		assertThat(new DefaultHyperlink(1).getText(), is(FormView.HYPERLINK_PREFIX + FormView.SECTION_B));
	}
	
	@Test
	public void titleConsturctor() {
		assertThat(new DefaultHyperlink(FormView.HYPERLINK_PREFIX + FormView.SECTION_B).getText(), is(FormView.HYPERLINK_PREFIX + FormView.SECTION_B));
	}
	
	@Test
	public void defaultConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultHyperlink(rc).getText(), is(FormView.HYPERLINK_PREFIX + FormView.SECTION_C));
	}
	
	@Test
	public void indexedConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultHyperlink(rc, 1).getText(), is(FormView.HYPERLINK_PREFIX + FormView.SECTION_D));
	}
	
	@Test
	public void titleConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultHyperlink(rc, FormView.HYPERLINK_PREFIX + FormView.SECTION_C).getText(), is(FormView.HYPERLINK_PREFIX + FormView.SECTION_C));
	}
	
	@Test(expected=SWTLayerException.class)
	public void testInvalidInstance() {
		new DefaultHyperlink("Non existing hyperlink");
	}
	
	@Test
	public void activate() {
		Hyperlink link = new DefaultHyperlink();
		link.activate();
		assertNotNull(new DefaultLabel(FormView.ACTIVATED_HYPERLINK));
	}
}
