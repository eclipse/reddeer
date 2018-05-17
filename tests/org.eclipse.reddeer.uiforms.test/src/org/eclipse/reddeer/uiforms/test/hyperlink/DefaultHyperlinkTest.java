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
package org.eclipse.reddeer.uiforms.test.hyperlink;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.label.DefaultLabel;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.uiforms.api.Hyperlink;
import org.eclipse.reddeer.uiforms.impl.form.DefaultForm;
import org.eclipse.reddeer.uiforms.impl.hyperlink.DefaultHyperlink;
import org.eclipse.reddeer.uiforms.test.ui.views.FormView;
import org.eclipse.reddeer.uiforms.test.ui.views.UIFormView;
import org.eclipse.reddeer.workbench.api.View;
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
	
	@Test(expected=CoreLayerException.class)
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
