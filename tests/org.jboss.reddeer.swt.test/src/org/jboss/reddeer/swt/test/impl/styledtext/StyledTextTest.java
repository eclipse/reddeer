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
package org.jboss.reddeer.swt.test.impl.styledtext;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.api.StyledText;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
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
		new org.eclipse.swt.custom.StyledText(shell, SWT.FULL_SELECTION);
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
	
	

}
