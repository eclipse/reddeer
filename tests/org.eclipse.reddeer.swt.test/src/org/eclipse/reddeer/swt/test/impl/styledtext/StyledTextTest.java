/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.test.impl.styledtext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.reddeer.swt.api.StyledText;
import org.eclipse.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.eclipse.reddeer.swt.test.SWTLayerTestCase;
import org.eclipse.swt.SWT;
import org.junit.Before;
import org.junit.Test;

public class StyledTextTest extends SWTLayerTestCase{
	
	@Before
	public void prepareStyledText(){
		StyledText t = new DefaultStyledText();
		t.setText("this is styled text");
	}
	
	@Override
	protected void createControls(org.eclipse.swt.widgets.Shell shell) {
		org.eclipse.swt.custom.StyledText t = new org.eclipse.swt.custom.StyledText(shell, SWT.FULL_SELECTION);
		org.eclipse.swt.widgets.Menu menu = new org.eclipse.swt.widgets.Menu(t);
		t.setMenu(menu);
	}
	
	@Test
	public void selectText(){
		StyledText t = new DefaultStyledText();
		t.selectText("styled");
		assertEquals("styled", t.getSelectionText());
	}
	
	@Test
	public void selectPosition(){
		StyledText t = new DefaultStyledText();
		t.setSelection(8, 10);
		assertEquals("st", t.getSelectionText());
	}
	
	@Test
	public void openContextMenu() {
		DefaultStyledText t = new DefaultStyledText();
		try {
			t.getContextMenu();
		} catch (Exception e) {
			fail("Can't open context menu.\n" + e.toString());
		}
	}
}
