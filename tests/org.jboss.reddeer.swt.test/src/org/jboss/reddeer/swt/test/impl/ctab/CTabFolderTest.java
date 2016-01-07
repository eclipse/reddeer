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
package org.jboss.reddeer.swt.test.impl.ctab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.swt.api.CTabItem;
import org.jboss.reddeer.swt.impl.ctab.DefaultCTabFolder;
import org.jboss.reddeer.swt.impl.ctab.DefaultCTabItem;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.junit.Test;

public class CTabFolderTest extends SWTLayerTestCase{
	private static final String ITEM_LABEL_PREFIX = "Item ";
	private static final String TOOLTIP_PREFIX = "Tool for Item ";
	private static final String CONTENT_PREFIX = "Content for Item ";
	
	@Override
	protected void createControls(Shell shell){
		shell.setLayout(new FillLayout());
		CTabFolder folder = new CTabFolder(shell, SWT.BORDER);
		for (int i = 0; i < 4; i++) {
			org.eclipse.swt.custom.CTabItem item = new org.eclipse.swt.custom.CTabItem(folder, SWT.CLOSE);
			item.setText(CTabFolderTest.ITEM_LABEL_PREFIX + i);
			item.setToolTipText(CTabFolderTest.TOOLTIP_PREFIX + i);
			Text text = new Text(folder, SWT.MULTI);
			text.setText(CTabFolderTest.CONTENT_PREFIX +i);
			item.setControl(text);
		}
	}
	
	@Test
	public void findByIndexAndActivate(){
		int index = 2;
		CTabItem tabItem = new DefaultCTabItem(index);
		tabItem.activate();
		String expectedCTabItemContent = CTabFolderTest.CONTENT_PREFIX + index;
		String cTabItemContent = new DefaultText(0).getText(); 
		assertTrue("cTabItem content is " + cTabItemContent
				+ "\nbut expected CTabItem content is " + expectedCTabItemContent,
			cTabItemContent.equals(expectedCTabItemContent));
		assertEquals(tabItem, new DefaultCTabFolder().getSelection());
	}
	
	@Test
	public void findByNameAndActivate(){
		int index = 1;
		CTabItem tabItem = new DefaultCTabItem(CTabFolderTest.ITEM_LABEL_PREFIX + index);
		tabItem.activate();
		String expectedCTabItemContent = CTabFolderTest.CONTENT_PREFIX + index;
		String cTabItemContent = new DefaultText(0).getText(); 
		assertTrue("cTabItem content is " + cTabItemContent
				+ "\nbut expected CTabItem content is " + expectedCTabItemContent,
			cTabItemContent.equals(expectedCTabItemContent));
		assertEquals(tabItem, new DefaultCTabFolder().getSelection());
	}
	@Test(expected = CoreLayerException.class)
	public void findNonExistingByIndex(){
		new DefaultCTabItem(5);
	}
	@Test(expected = CoreLayerException.class)
	public void findNonExistingByLabel(){
		new DefaultCTabItem("NON_EXISTING_#$");
	}
	@Test
	public void close(){
		int index = 3;
		CTabItem cTabItem = new DefaultCTabItem(index);
		cTabItem.close();
		try{
			new DefaultCTabItem(index);
			fail("CTabItem with index " + index + " was found but has to be closed");
		}
		catch (CoreLayerException sle){
			// do nothing closed CTabItem should not be found
		}
	}
}
