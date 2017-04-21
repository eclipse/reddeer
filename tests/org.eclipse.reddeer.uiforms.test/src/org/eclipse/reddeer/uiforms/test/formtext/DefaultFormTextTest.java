/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.uiforms.test.formtext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.label.DefaultLabel;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.uiforms.api.FormText;
import org.eclipse.reddeer.uiforms.impl.form.DefaultForm;
import org.eclipse.reddeer.uiforms.impl.formtext.DefaultFormText;
import org.eclipse.reddeer.uiforms.test.ui.views.FormView;
import org.eclipse.reddeer.uiforms.test.ui.views.UIFormView;
import org.eclipse.reddeer.workbench.api.View;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultFormTextTest {

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

	@Test(expected = CoreLayerException.class)
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
		assertTrue(formText.isFocusControl());
	}

	@Test
	public void hasFocusFalseTest() {
		FormText formText0 = new DefaultFormText(0);
		FormText formText1 = new DefaultFormText(1);
		formText1.setFocus();
		assertFalse(formText0.isFocusControl());
	}

	@Test
	public void getTooltipTest() {
		FormText formText = new DefaultFormText();
		assertThat(formText.getToolTipText(), is(FormView.FORMTEXT_PREFIX
				+ FormView.SECTION_A + "tooltip"));
	}

}
