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

package org.eclipse.reddeer.ui.test.wizard.impl;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;

/**
 * Second page of the new RedDeer Test Case wizard
 * @author jrichter
 *
 */
public class RedDeerTestCaseWizardPageTwo extends WizardPage {
	
	public RedDeerTestCaseWizardPageTwo(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Clicks the Select All button
	 */
	public void selectAll() {
		new PushButton(this, "Select All").click();
	}
	
	/**
	 * Clicks the Deselect All button
	 */
	public void deselectAll() {
		new PushButton(this, "Deselect All").click();
	}
	
	/**
	 * Sets if the Create final method stubs checkbox is checked
	 * @param checked true to check, false otherwise
	 */
	public void setCreateFinalMethodStubs(boolean checked) {
		new CheckBox(this, "Create final method stubs").toggle(checked);
	}
	
	/**
	 * Sets if the Create tasks for generated test methods checkbox is checked
	 * @param checked true to check, false otherwise
	 */
	public void setCreateTasksForMethods(boolean checked) {
		new CheckBox(this, "Create tasks for generated test methods").toggle(checked);
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
		for (TreeItem treeItem : new DefaultTree(this).getAllItems()) {
			if (matcher.matches(treeItem.getText())) {
				treeItem.setChecked(true);
			}
		}
	}
}
