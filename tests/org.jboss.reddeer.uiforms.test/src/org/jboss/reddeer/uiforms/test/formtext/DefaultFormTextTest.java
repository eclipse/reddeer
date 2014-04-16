package org.jboss.reddeer.uiforms.test.formtext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.uiforms.api.FormText;
import org.jboss.reddeer.uiforms.impl.form.DefaultForm;
import org.jboss.reddeer.uiforms.impl.formtext.DefaultFormText;
import org.jboss.reddeer.uiforms.test.ui.views.FormView;
import org.jboss.reddeer.uiforms.test.ui.views.UIFormView;
import org.jboss.reddeer.workbench.api.View;
import org.junit.Before;
import org.junit.Test;

public class DefaultFormTextTest extends RedDeerTest {

	private View uiFormView = new UIFormView();

	@Before
	public void openView() {
		uiFormView.open();
	}

	@Test
	public void defaultConstructor() {
		assertThat(new DefaultFormText().getText(), is(FormView.FORMTEXT_PREFIX
				+ FormView.SECTION_A));
	}

	@Test
	public void indexedConstructor() {
		assertThat(new DefaultFormText(1).getText(),
				is(FormView.FORMTEXT_PREFIX + FormView.SECTION_B));
	}

	@Test
	public void titleConstructor() {
		assertThat(new DefaultFormText(FormView.FORMTEXT_PREFIX
				+ FormView.SECTION_A).getText(), is(FormView.FORMTEXT_PREFIX
				+ FormView.SECTION_A));
	}

	@Test
	public void defaultConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultFormText(rc).getText(),
				is(FormView.FORMTEXT_PREFIX + FormView.SECTION_C));
	}

	@Test
	public void indexedConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultFormText(rc, 1).getText(),
				is(FormView.FORMTEXT_PREFIX + FormView.SECTION_D));
	}

	@Test
	public void titleConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultFormText(rc, FormView.FORMTEXT_PREFIX
				+ FormView.SECTION_C).getText(), is(FormView.FORMTEXT_PREFIX
				+ FormView.SECTION_C));
	}

	@Test(expected = SWTLayerException.class)
	public void testInvalidInstance() {
		new DefaultFormText("Non existing hyperlink");
	}

	@Test
	public void clickTest() {
		FormText formText = new DefaultFormText();
		formText.click();
		assertNotNull(new DefaultLabel(FormView.ACTIVATED_FORMTEXT));
	}

	@Test
	public void hasFocusTrueTest() {
		FormText formText = new DefaultFormText();
		formText.setFocus();
		assertTrue(formText.hasFocus());
	}

	@Test
	public void hasFocusFalseTest() {
		FormText formText0 = new DefaultFormText(0);
		FormText formText1 = new DefaultFormText(1);
		formText1.setFocus();
		assertFalse(formText0.hasFocus());
	}

	@Test
	public void getTooltipTest() {
		FormText formText = new DefaultFormText();
		assertThat(formText.getTooltipText(), is(FormView.FORMTEXT_PREFIX
				+ FormView.SECTION_A + "tooltip"));
	}

}
