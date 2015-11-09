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

package org.jboss.reddeer.ui.test.wizard.impl;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;

/**
 * Second page of the new RedDeer Test Case wizard
 * @author jrichter
 *
 */
public class RedDeerTestCaseWizardPageTwo extends WizardPage {
	
	/**
	 * Clicks the Select All button
	 */
	public void selectAll() {
		new PushButton("Select All").click();
	}
	
	/**
	 * Clicks the Deselect All button
	 */
	public void deselectAll() {
		new PushButton("Deselect All").click();
	}
	
	/**
	 * Sets if the Create final method stubs checkbox is checked
	 * @param checked true to check, false otherwise
	 */
	public void setCreateFinalMethodStubs(boolean checked) {
		new CheckBox("Create final method stubs").toggle(checked);
	}
	
	/**
	 * Sets if the Create tasks for generated test methods checkbox is checked
	 * @param checked true to check, false otherwise
	 */
	public void setCreateTasksForMethods(boolean checked) {
		new CheckBox("Create tasks for generated test methods").toggle(checked);
	}
	
	/**
	 * Selects items with a given text
	 * @param item text of the items to be selected
	 */
	@SuppressWarnings("unchecked")
	public void selectItemsWithText(String item) {		
		selectMatchingItems(new IsEqual<String>(item));
	}
	
	/**
	 * Selects items with text matching matchers
	 * @param matchers
	 */
	@SuppressWarnings("unchecked")
	public void selectMatchingItems(Matcher<String>... matchers) {
		Matcher<String> matcher = Matchers.allOf(matchers);
		for (TreeItem treeItem : new DefaultTree().getAllItems()) {
			if (matcher.matches(treeItem.getText())) {
				treeItem.setChecked(true);
			}
		}
	}
}
