package org.jboss.reddeer.uiforms.test.expandablecomposite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.uiforms.impl.expandablecomposite.DefaultExpandableComposite;
import org.jboss.reddeer.uiforms.impl.form.DefaultForm;
import org.jboss.reddeer.uiforms.test.ui.views.FormView;
import org.jboss.reddeer.uiforms.test.ui.views.UIFormView;
import org.jboss.reddeer.workbench.api.View;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultExpandableCompositeTest {

	private View uiFormView = new UIFormView();

	@Before
	public void openView() {
		uiFormView.open();
	}

	@Test
	public void defaultConsturctor() {
		assertThat(new DefaultExpandableComposite().getText(), is(FormView.EXPANDABLE_COMPOSITE_A));
	}

	@Test
	public void indexedConsturctor() {
		assertThat(new DefaultExpandableComposite(1).getText(), is(FormView.EXPANDABLE_COMPOSITE_B));
	}

	@Test
	public void titleConsturctor() {
		assertThat(new DefaultExpandableComposite(FormView.EXPANDABLE_COMPOSITE_C).getText(), is(FormView.EXPANDABLE_COMPOSITE_C));
	}

	@Test
	public void defaultConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultExpandableComposite(rc).getText(), is(FormView.EXPANDABLE_COMPOSITE_C));
	}

	@Test
	public void indexedConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultExpandableComposite(rc, 1).getText(), is(FormView.EXPANDABLE_COMPOSITE_D));
	}

	@Test
	public void titleConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultExpandableComposite(rc, FormView.EXPANDABLE_COMPOSITE_C).getText(), is(FormView.EXPANDABLE_COMPOSITE_C));
	}

	@Test(expected=SWTLayerException.class)
	public void testInvalidInstance() {
		new DefaultExpandableComposite("Non existing expandable composite title");
	}

	@Test
	public void testExpanded_true(){
		DefaultExpandableComposite composite = new DefaultExpandableComposite();
		composite.setExpanded(true);

		assertTrue(composite.isExpanded());

		assertNotNull(new DefaultText(composite, "Value: "));
	}

	@Test(expected=SWTLayerException.class)
	public void testExpanded_false(){
		DefaultExpandableComposite composite = new DefaultExpandableComposite();
		composite.setExpanded(false);

		assertFalse(composite.isExpanded());

		new DefaultText(composite, "Value: ");
	}
}
