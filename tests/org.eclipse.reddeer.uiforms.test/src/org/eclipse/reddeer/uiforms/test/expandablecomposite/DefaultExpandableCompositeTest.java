/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.uiforms.test.expandablecomposite;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.uiforms.impl.expandablecomposite.DefaultExpandableComposite;
import org.eclipse.reddeer.uiforms.impl.form.DefaultForm;
import org.eclipse.reddeer.uiforms.test.ui.views.FormView;
import org.eclipse.reddeer.uiforms.test.ui.views.UIFormView;
import org.eclipse.reddeer.workbench.api.View;
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

	@Test(expected=CoreLayerException.class)
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

	@Test(expected=CoreLayerException.class)
	public void testExpanded_false(){
		DefaultExpandableComposite composite = new DefaultExpandableComposite();
		composite.setExpanded(false);

		assertFalse(composite.isExpanded());

		new DefaultText(composite, "Value: ");
	}
}
