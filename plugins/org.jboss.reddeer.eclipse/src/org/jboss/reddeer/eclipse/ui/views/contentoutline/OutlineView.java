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
package org.jboss.reddeer.eclipse.ui.views.contentoutline;

import java.util.ArrayList;
import java.util.Collection;

import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.matcher.WithTooltipTextMatcher;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.TreeHasChildren;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
/**
 * Represents Outline view in Eclipse
 * 
 * @author jjankovi
 *
 */
public class OutlineView extends WorkbenchView {

	/**
	 * Construct the view with "Outline".
	 */
	public OutlineView() {
		super("Outline");
	}

	/**
	 * Returns collection of outline elements.
	 * 
	 * @return Collection of outline elements
	 */
	public Collection<TreeItem> outlineElements() {
		activate();
		return getTreeForView();
	}

	/**
	 * Clicks on tooltip "Collapse All".
	 */
	public void collapseAll() {
		activate();
		clickOnToolTip("Collapse All.*");
	}

	/**
	 * Clicks on tooltip "Sort".
	 */
	public void sort() {
		activate();
		clickOnToolTip("Sort.*");
	}
	
	/**
	 * Clicks on tooltip "Hide Fields".
	 */
	public void hideFields() {
		activate();
		clickOnToolTip("Hide Fields.*");
	}

	/**
	 * Clicks on tooltip "Hide Static Fields and Methods".
	 */
	public void hideStaticFieldsAndMethods() {
		activate();
		clickOnToolTip("Hide Static Fields and Methods.*");
	}

	/**
	 * Clicks on tooltip "Hide Non-Public Members".
	 */
	public void hideNonPublicMembers() {
		activate();
		clickOnToolTip("Hide Non-Public Members.*");
	}

	/**
	 * Clicks on tooltip "Hide Local Types".
	 */
	public void hideLocalTypes() {
		activate();
		clickOnToolTip("Hide Local Types.*");
	}

	/**
	 * Clicks on tooltip "Link with Editor".
	 */
	public void linkWithEditor() {
		activate();
		clickOnToolTip("Link with Editor.*");
	}
	
	private Collection<TreeItem>getTreeForView() {
		try {
			DefaultTree tree = new DefaultTree();
			new WaitUntil(new TreeHasChildren(tree),TimePeriod.NORMAL,false);

			return tree.getItems();
		} catch (CoreLayerException exc) {
			return new ArrayList<TreeItem>();
		}
		
	}
	
	private void clickOnToolTip(String regex) {
		WithTooltipTextMatcher rm = new WithTooltipTextMatcher(new RegexMatcher(regex));
		new DefaultToolItem(rm).click();
	}
	
}
