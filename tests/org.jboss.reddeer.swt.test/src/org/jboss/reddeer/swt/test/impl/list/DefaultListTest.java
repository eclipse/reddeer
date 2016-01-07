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
package org.jboss.reddeer.swt.test.impl.list;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.List;
import org.jboss.reddeer.swt.impl.list.DefaultList;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.junit.Test;

public class DefaultListTest extends SWTLayerTestCase {

	@Override
	public void createControls(Shell shell) {
		final org.eclipse.swt.widgets.List list = 
				new org.eclipse.swt.widgets.List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		for (int i = 0; i < 5; i++) {
			list.add ("Item " + i);
		}
		list.setVisible(true);
		shell.pack();
	}
	
	@Test
	public void testGetListItems() {
		List list = new DefaultList();
		String[] items= list.getListItems();
		for (int i = 0; i < 5; i++) {
			assertTrue("Item " + i + " is not contained in returned list.", 
					Arrays.asList(items).contains("Item " + i));
		}
	}
	
	@Test
	public void testSelectSpecificItem() {
		List list = new DefaultList();
		list.select(2);
	
		assertTrue("Item number 2 is not selected.", list.getSelectionIndex() == 2);
		
		list.select(3);
		
		assertFalse("Item number 2 should not be selected.", list.getSelectionIndex() == 2);
		assertTrue("Item number 3 is not selected.", list.getSelectionIndex() == 3);
	}
	
	@Test
	public void testSelectSpecificItems() {
		List list = new DefaultList();
		list.select(0, 1, 3);
		java.util.List<Integer> indices = Arrays.asList(list.getSelectionIndices()[0], list.getSelectionIndices()[1],
				list.getSelectionIndices()[2]);
		
		assertTrue("Items with indices 0, 1 and 3 are not selected.", indices.contains(0) && indices.contains(1) 
				&& indices.contains(3));
		
		list.select(2);
		
		assertTrue("Items was not unselected before next selection.", list.getSelectedItems().length == 1);
	}
	
	@Test
	public void testGetIndex() {
		List list = new DefaultList();
		list.select(1);
		
		assertTrue("Items with indices 0, 1 and 3 are not selected.", list.getSelectionIndex() == 1);
	}
	
	@Test
	public void testDeselectAllItems() {
		List list = new DefaultList();
		list.select(0, 1, 2);
		list.deselectAll();
		
		assertTrue("There are still some selected items altought there should be none.", list.getSelectedItems().length == 0);
	}
	
	@Test
	public void testSelectAllItems() {
		List list = new DefaultList();
		list.select(0, 1, 2);
		list.selectAll();
		
		assertTrue("All items should be selected.", list.getSelectedItems().length == 5);
	}
}
