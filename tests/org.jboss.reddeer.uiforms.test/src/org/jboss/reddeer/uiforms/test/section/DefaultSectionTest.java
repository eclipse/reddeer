/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.uiforms.test.section;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.uiforms.impl.form.DefaultForm;
import org.jboss.reddeer.uiforms.impl.section.DefaultSection;
import org.jboss.reddeer.uiforms.test.ui.views.FormView;
import org.jboss.reddeer.uiforms.test.ui.views.UIFormView;
import org.jboss.reddeer.workbench.api.View;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultSectionTest {

	private View uiFormView = new UIFormView(); 
	
	@Before
	public void openView() {
		uiFormView.open();
	}
	
	@Test
	public void defaultConsturctor() {
		assertThat(new DefaultSection().getText(), is(FormView.SECTION_A));
	}
	
	@Test
	public void indexedConsturctor() {
		assertThat(new DefaultSection(1).getText(), is(FormView.SECTION_B));
	}
	
	@Test
	public void titleConsturctor() {
		assertThat(new DefaultSection(FormView.SECTION_A).getText(), is(FormView.SECTION_A));
	}
	
	@Test
	public void defaultConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultSection(rc).getText(), is(FormView.SECTION_C));
	}
	
	@Test
	public void indexedConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultSection(rc, 1).getText(), is(FormView.SECTION_D));
	}
	
	@Test
	public void titleConsturctor_referencedComposite() {
		ReferencedComposite rc = new DefaultForm(FormView.FORM_B_TITLE);
		assertThat(new DefaultSection(rc, FormView.SECTION_C).getText(), is(FormView.SECTION_C));
	}
	
	@Test(expected=CoreLayerException.class)
	public void testInvalidInstance() {
		new DefaultSection("Non existing section title");
	}
	
	@Test
	public void testExpanded_true(){
		DefaultSection section = new DefaultSection();
		section.setExpanded(true);

		assertNotNull(new DefaultText(section, "Value: "));
	}
	
	@Test(expected=CoreLayerException.class)
	public void testExpanded_false(){
		DefaultSection section = new DefaultSection();
		section.setExpanded(false);

		new DefaultText(section, "Value: ");
	}
}
