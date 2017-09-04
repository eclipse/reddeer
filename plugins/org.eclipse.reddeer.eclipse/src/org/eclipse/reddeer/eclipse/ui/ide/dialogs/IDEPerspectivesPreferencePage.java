/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.ui.ide.dialogs;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.group.DefaultGroup;

/** Class represents Perspectives preference page.
 * 
 * @author Vlado Pakan
 *
 */
public class IDEPerspectivesPreferencePage extends PreferencePage {

	/** 
	 * Constructs the preference page with "General &gt; Perspectives".
	 */
	public IDEPerspectivesPreferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite, new String[] {"General", "Perspectives"});
	}

	/** 
	 * Returns Open a new perspective in the same window radio button.
	 * 
	 * @return RadioButton
	 */
	private RadioButton getOpenNewPerspectiveInSameWindowRadioButton() {
		return new RadioButton(new DefaultGroup(this, "Open a new perspective"),
				"In the same window");
	}

	/** 
	 * Returns Open a new perspective in new window radio button.
	 *  
	 * @return RadioButton
	 */
	private RadioButton getOpenNewPerspectiveInNewWindowRadioButton() {
		return new RadioButton(new DefaultGroup(this, "Open a new perspective"),
				"In a new window");
	}

	/** 
	 * Returns Always Open the associated perspective when creating a new
	 * project radio button.
	 * 
	 * @return RadioButton
	 */
	private RadioButton getAlwaysOpenAssociatedPerspectiveRadioButton() {
		return new RadioButton(new DefaultGroup(this, 
				"Open the associated perspective when creating a new project"),
				"Always open");
	}

	/** 
	 * Returns Newer Open the associated perspective when creating a new project
	 * radio button.
	 * 
	 * @return RadioButton
	 */
	private RadioButton getNeverOpenAssociatedPerspectiveRadioButton() {
		return new RadioButton(new DefaultGroup(this,
				"Open the associated perspective when creating a new project"),
				"Never open");
	}

	/** 
	 * Returns Prompt Open the associated perspective when creating a new
	 * project radio button.
	 * 
	 * @return RadioButton
	 */
	private RadioButton getPromptOpenAssociatedPerspectiveRadioButton() {
		return new RadioButton(new DefaultGroup(this,
				"Open the associated perspective when creating a new project"),
				"Prompt");
	}

	/** 
	 * Returns Open a new perspective in the same window value.
	 * 
	 * @return boolean
	 */
	public boolean isOpenNewPerspectiveInSameWindow() {
		return getOpenNewPerspectiveInSameWindowRadioButton().isSelected();
	}

	/** 
	 * Returns Open a new perspective in new window value.
	 * 
	 * @return boolean
	 */
	public boolean isOpenNewPerspectiveInNewWindow() {
		return getOpenNewPerspectiveInNewWindowRadioButton().isSelected();
	}

	/** 
	 * Returns Always Open the associated perspective when creating a new
	 * project value.
	 * 
	 * @return boolean
	 */
	public boolean isAlwaysOpenAssociatedPerspective() {
		return getAlwaysOpenAssociatedPerspectiveRadioButton().isSelected();
	}

	/** 
	 * Returns Newer Open the associated perspective when creating a new project
	 * value.
	 * 
	 * @return boolean
	 */
	public boolean isNeverOpenAssociatedPerspective() {
		return getNeverOpenAssociatedPerspectiveRadioButton().isSelected();
	}

	/** 
	 * Returns Prompt Open the associated perspective when creating a new
	 * project value.
	 * 
	 * @return boolean
	 */
	public boolean isPromptOpenAssociatedPerspective() {
		return getPromptOpenAssociatedPerspectiveRadioButton().isSelected();
	}

	/** 
	 * Check Open a new perspective in the same window.
	 */
	public IDEPerspectivesPreferencePage checkOpenNewPerspectiveInSameWindow() {
		getOpenNewPerspectiveInSameWindowRadioButton().toggle(true);
		return this;
	}

	/**
	 *  Checks Open a new perspective in new window.
	 */
	public IDEPerspectivesPreferencePage checkOpenNewPerspectiveInNewWindow() {
		getOpenNewPerspectiveInNewWindowRadioButton().toggle(true);
		return this;
	}

	/**
	 *  Checks Always Open the associated perspective when creating a new project.
	 */
	public IDEPerspectivesPreferencePage checkAlwaysOpenAssociatedPerspective() {
		getAlwaysOpenAssociatedPerspectiveRadioButton().toggle(true);
		return this;
	}

	/** 
	 * Checks Newer Open the associated perspective when creating a new project.
	 */
	public IDEPerspectivesPreferencePage checkNeverOpenAssociatedPerspective() {
		getNeverOpenAssociatedPerspectiveRadioButton().toggle(true);
		return this;
	}

	/** 
	 * Checks Prompt Open the associated perspective when creating a new project.
	 */
	public IDEPerspectivesPreferencePage checkPromptOpenAssociatedPerspective() {
		getPromptOpenAssociatedPerspectiveRadioButton().toggle(true);
		return this;
	}
}
