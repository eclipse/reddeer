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
package org.jboss.reddeer.jface.test.viewer;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jboss.reddeer.jface.exception.JFaceLayerException;
import org.jboss.reddeer.jface.handler.TreeViewerHandler;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class TreeViewerHandlerTest extends PrepareTreeWithStyledItems {
	
	private TreeViewerHandler treeViewerHandler = TreeViewerHandler.getInstance();
	
	private String[] pathXXX = new String[] {"item x", "item xx", "item xxx"};
	private String[] path000 = new String[] {"item 0", "item 00", "item 000"};
	private String[] path111 = new String[] {"item 1", "item 11", "item 111"};
	
	@Test
	public void testGetNonstyledItem000() {
		TreeItem item = treeViewerHandler.getTreeItem(new DefaultTree(), path000);
		assertTrue("There should be no styles for nonstyled item", treeViewerHandler.getStyledTexts(item) == null);
	}
	
	@Test(expected = JFaceLayerException.class)
	public void getNonexistingItem() {
		treeViewerHandler.getTreeItem(new DefaultTreeItem("item 0"), "item 10");
	}
	
	@Test
	public void getStyledTextOfItem111() {
		TreeItem item = treeViewerHandler.getTreeItem(new DefaultTree(), path111);
		assertTrue("There should be 2 styles for the tree but there are not",
				treeViewerHandler.getStyledTexts(item).length == 2);
		assertTrue("Style texts does not match", treeViewerHandler.getStyledTexts(item)[0].equals("prefix") &&
				treeViewerHandler.getStyledTexts(item)[1].equals("postfix"));
	}
	
	@Test(expected = JFaceLayerException.class)
	public void getDuplicativeItemX() {
		treeViewerHandler.getTreeItem(new DefaultTree(), "item x");
	}
	
	@Test
	public void getDuplicativeItemsXXX() {
		List<TreeItem> items = treeViewerHandler.getTreeItems(new DefaultTree(), pathXXX);
		int size = items.size();
		assertTrue("There should be precisely 8 duplicative items, but there is/are " + size + " item(s)", 
				items.size() == 8);
	}
	
	@Test
	public void getDuplicativeItemsXY() {
		List<TreeItem> items = treeViewerHandler.getTreeItems(new DefaultTree(), "item x", "item xy");
		int size = items.size();
		assertTrue("There should be precisely 4 duplicative items, but there is/are " + size + " item(s)", 
				size == 4);
	}
	
	@Test
	public void getDuplicativeItemsX() {
		assertTrue("There should be precisely two items retrieved, but it did not happened",
				treeViewerHandler.getTreeItems(new DefaultTree(), "item x").size() == 2);
	}
	
	@Test
	public void getDuplicativeItemsFromItemXY() {
		TreeItem itemXY = treeViewerHandler.getTreeItems(new DefaultTree(), "item x", "item xy").get(0);
		int size = treeViewerHandler.getTreeItems(itemXY, "item xyy").size();
		assertTrue("There should be precisely 2 duplicative items, but there is/are " + size + "item(s)",
				size == 2);
	}
	
	@Test
	public void getStyledTextsOfItemXXX() {
		String[] styles = treeViewerHandler.getStyledTexts(
				treeViewerHandler.getTreeItems(new DefaultTree(), pathXXX).get(0));
		assertTrue("There should be styled prefix and postfix", styles[0].equals("prefix") && 
				styles[1].equals("postfix"));
	}
	
	@Test(expected = JFaceLayerException.class)
	public void getNonExistingItems() {
		treeViewerHandler.getTreeItems(new DefaultTree(), "non", "existing", "path");
	}
}
