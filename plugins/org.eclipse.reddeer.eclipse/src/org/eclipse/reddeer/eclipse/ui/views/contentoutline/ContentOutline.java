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
package org.eclipse.reddeer.eclipse.ui.views.contentoutline;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.WithTooltipTextMatcher;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.TreeHasChildren;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;
/**
 * Represents Outline view in Eclipse
 * 
 * @author jjankovi
 *
 */
public class ContentOutline extends WorkbenchView {

	/**
	 * Construct the view with "Outline".
	 */
	public ContentOutline() {
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
			DefaultTree tree = new DefaultTree(cTabItem);
			new WaitUntil(new TreeHasChildren(tree),TimePeriod.DEFAULT,false);

			return tree.getItems();
		} catch (CoreLayerException exc) {
			return new ArrayList<TreeItem>();
		}
		
	}
	
	private void clickOnToolTip(String regex) {
		WithTooltipTextMatcher rm = new WithTooltipTextMatcher(new RegexMatcher(regex));
		new DefaultToolItem(cTabItem.getFolder(), rm).click();
	}
	
}
