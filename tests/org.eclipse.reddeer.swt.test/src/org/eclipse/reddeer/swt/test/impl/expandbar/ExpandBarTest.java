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
package org.eclipse.reddeer.swt.test.impl.expandbar;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.reddeer.swt.api.ExpandBar;
import org.eclipse.reddeer.swt.api.ExpandItem;
import org.eclipse.reddeer.swt.exception.SWTLayerException;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.swt.impl.expandbar.DefaultExpandBar;
import org.eclipse.reddeer.swt.impl.expandbar.DefaultExpandItem;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.test.SWTLayerTestCase;
import org.junit.Test;

public class ExpandBarTest extends SWTLayerTestCase{
	private static final String EXPAND_ITEM_LABEL_PREFIX = "Expand Item: ";
	private static final String TEXT_LABEL_PREFIX = "Text: ";
	private static final String TEXT_VALUE_PREFIX = "EB:2 TX: ";
	private static final int EXPAND_BAR_0_NUM_ITEMS = 3;
	private static final int EXPAND_BAR_1_NUM_ITEMS = 5;
	
	@Override
	protected void createControls(Shell shell){
		org.eclipse.swt.widgets.ExpandBar bar = new org.eclipse.swt.widgets.ExpandBar(shell, SWT.V_SCROLL);
		for (int itemIndex = 0 ; itemIndex < ExpandBarTest.EXPAND_BAR_0_NUM_ITEMS ; itemIndex++){
			Composite composite = new Composite (bar, SWT.NONE);
			GridLayout layout = new GridLayout (2,true);
			composite.setLayout(layout);
			for (int rowIndex = 0 ; rowIndex < 5 ; rowIndex++){
				Label label = new Label (composite, SWT.PUSH);
				label.setText(ExpandBarTest.TEXT_LABEL_PREFIX + itemIndex + rowIndex);
				new Text (composite, SWT.BORDER);
			}
			org.eclipse.swt.widgets.ExpandItem item = new org.eclipse.swt.widgets.ExpandItem (bar, SWT.NONE, 0);
			item.setText(ExpandBarTest.EXPAND_ITEM_LABEL_PREFIX + itemIndex);
			item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item.setControl(composite);
		}
		bar = new org.eclipse.swt.widgets.ExpandBar (shell, SWT.V_SCROLL);
		for (int itemIndex = 0 ; itemIndex < ExpandBarTest.EXPAND_BAR_1_NUM_ITEMS ; itemIndex++){
			Composite composite = new Composite (bar, SWT.NONE);
			GridLayout layout = new GridLayout (2,true);
			composite.setLayout(layout);
			for (int rowIndex = 0 ; rowIndex < 5 ; rowIndex++){
				Label label = new Label (composite, SWT.PUSH);
				label.setText(ExpandBarTest.TEXT_LABEL_PREFIX + itemIndex + rowIndex);
				Text text = new Text (composite, SWT.BORDER);
				text.setText(ExpandBarTest.TEXT_VALUE_PREFIX + itemIndex + rowIndex);
			}
			org.eclipse.swt.widgets.ExpandItem item = new org.eclipse.swt.widgets.ExpandItem (bar, SWT.NONE, 0);
			item.setText(ExpandBarTest.EXPAND_ITEM_LABEL_PREFIX + itemIndex);
			item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item.setControl(composite);
		}
	}
	
	@Test
	public void findByIndexAndExpand(){
		final int expandBarIndex = 1;
		final int expandBarItemIndex = 0;
		ExpandItem expandBarItem = new DefaultExpandItem(expandBarIndex,expandBarItemIndex);
		expandBarItem.expand();
		assertTrue("Expand bar item " + expandBarItem.getText() + " is not expanded",
			expandBarItem.isExpanded());
		final int textIndex = 1;
		final String textOfTextWidget = new DefaultText(textIndex).getText();
		// test if proper Expand Bar Item was expanded via checking content
		// of Text widget which should be displayed within expanded Expand Bar Item
		assertTrue("Incorrect Text Wiget was found",
			textOfTextWidget.equals(ExpandBarTest.TEXT_VALUE_PREFIX + expandBarItemIndex + textIndex));		
	}
	@Test
	public void findByNameAndExpand(){
		final int expandBarIndex = 1;
		final int expandBarItemIndex = 1;
		final String expandBarItemText = ExpandBarTest.EXPAND_ITEM_LABEL_PREFIX + expandBarItemIndex;
		ExpandItem expandBarItem = new DefaultExpandItem(expandBarIndex,expandBarItemText);
		expandBarItem.expand();
		assertTrue("Expand bar item " + expandBarItem.getText() + " is not expanded",
			expandBarItem.isExpanded());
		final int textIndex = 2;
		final String textOfTextWidget = new DefaultText(textIndex).getText();
		// test if proper Expand Bar Item was expanded via checking content
		// of Text widget which should be displayed within expanded Expand Bar Item
		assertTrue("Incorrect Text Wiget was found",
			textOfTextWidget.equals(ExpandBarTest.TEXT_VALUE_PREFIX + expandBarItemIndex + textIndex));	
	}
	@Test(expected = CoreLayerException.class)
	public void findNonExistingByIndex(){
		new DefaultExpandItem(3,1);
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingByLabel(){
		new DefaultExpandItem("NON_EXISTING_#$");
	}
	@Test
	public void expandAll(){
		ExpandBar expandBar = new DefaultExpandBar();
		expandBar.expandAll();
		for (ExpandItem expandBarItem : expandBar.getItems()){
			assertTrue("Expand Bar Item " + expandBarItem.getText() + " is not expanded",
				expandBarItem.isExpanded());
		}
	}
	@Test
	public void collapseAll(){
		final int expandBarIndex = 1;
		ExpandBar expandBar = new DefaultExpandBar(expandBarIndex);
		new DefaultExpandItem(expandBarIndex, 0).expand();
		new DefaultExpandItem(expandBarIndex, 2).expand();
		new DefaultExpandItem(expandBarIndex, 4).expand();
		expandBar.collapseAll();
		for (ExpandItem expandBarItem : expandBar.getItems()){
			assertTrue("Expand Bar Item " + expandBarItem.getText() + " is not collapsed",
				!expandBarItem.isExpanded());
		}
	}
	@Test
	public void getItemsCount(){
		int numItems = new DefaultExpandBar().getItemsCount();
		assertTrue("First expand bar has to have " + ExpandBarTest.EXPAND_BAR_0_NUM_ITEMS
				+ " but has " + numItems,
			numItems == ExpandBarTest.EXPAND_BAR_0_NUM_ITEMS);
		numItems = new DefaultExpandBar(1).getItemsCount();
		assertTrue("First expand bar has to have " + ExpandBarTest.EXPAND_BAR_1_NUM_ITEMS
				+ " but has " + numItems,
			numItems == ExpandBarTest.EXPAND_BAR_1_NUM_ITEMS);
	}
	
	@Test
	public void getBarItems(){
		assertEquals(ExpandBarTest.EXPAND_BAR_0_NUM_ITEMS, new DefaultExpandBar().getItems().size());
	}
}
