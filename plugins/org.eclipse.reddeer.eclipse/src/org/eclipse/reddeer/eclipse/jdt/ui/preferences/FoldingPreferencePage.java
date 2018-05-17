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
package org.eclipse.reddeer.eclipse.jdt.ui.preferences;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;

/**
 * Class represents folding preference page in preference shell.
 * 
 * @author jjankovi
 * @since 0.5
 */
public class FoldingPreferencePage extends PreferencePage {

	/**
	 * Constructor which open folding preference in preference shell.
	 */
	public FoldingPreferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite, new String[] {"Java", "Editor", "Folding"});
	}
	
	/**
	 * Check whether folding is enabled or not.
	 * 
	 * @return true if folding is enabled, false otherwise
	 */
	public boolean isFoldingChecked() {
		return new CheckBox(this, 0).isChecked();
	}
	
	/**
	 * Enable folding. If folding is enabled, nothing happen.
	 */
	public FoldingPreferencePage enableFolding() {
		new CheckBox(this, 0).toggle(true);
		return this;
	}
	
	/**
	 * Disable folding. If folding is disabled, nothing happen.
	 */
	public FoldingPreferencePage disableFolding() {
		new CheckBox(this, 0).toggle(false);
		return this;
	}
	
	/**
	 * Check whether folding of comments is enabled or not.
	 * 
	 * @return true if folding of comments is enabled, false otherwise
	 */
	public boolean isCommentsChecked() {
		return new CheckBox(this, 1).isChecked();
	}
	
	/**
	 * Enable comments folding. If comments folding is enabled, nothing happen.
	 */
	public FoldingPreferencePage enableComments() {
		new CheckBox(this, 1).toggle(true);
		return this;
	}
	
	/**
	 * Disable comments folding. If comments folding is disabled, nothing happen.
	 */
	public FoldingPreferencePage disableComments() {
		new CheckBox(this, 1).toggle(false);
		return this;
	}
	
	/**
	 * Check whether header comments are folding or not.
	 * @return true if header comments are folding, false otherwise
	 */
	public boolean isHeaderCommentsChecked() {
		return new CheckBox(this, 2).isChecked();
	}
	
	/** 
	 * Enable header comments folding. If header comments folding is enabled, nothing happen.
	 */
	public FoldingPreferencePage enableHeaderComments() {
		new CheckBox(this, 2).toggle(true);
		return this;
	}
	
	/**
	 * Disable header comments folding. If header comments folding is disabled, nothing happen.
	 */
	public FoldingPreferencePage disableHeaderComments() {
		new CheckBox(this, 2).toggle(false);
		return this;
	}
	
	/**
	 * Check whether inner types are folding or not.
	 * @return true if inner types are folding, false otherwise
	 */
	public boolean isInnerTypesChecked() {
		return new CheckBox(this, 3).isChecked();
	}

	/**
	 * Enable inner types folding. If inner types folding is enabled, nothing happen.
	 */
	public FoldingPreferencePage enableInnerTypes() {
		new CheckBox(this, 3).toggle(true);
		return this;
	}
	
	/**
	 * Disable inner types folding. If inner types folding is disabled, nothing happen.
	 */
	public FoldingPreferencePage disableInnerTypes() {
		new CheckBox(this, 3).toggle(false);
		return this;
	}
	
	/**
	 * Check whether members are folding or not.
	 * @return true if members are folding, false otherwise
	 */
	public boolean isMembersChecked() {
		return new CheckBox(this, 4).isChecked();
	}

	/**
	 * Enable members folding. If members folding is enabled, nothing happen.
	 */
	public FoldingPreferencePage enableMembers() {
		new CheckBox(this, 4).toggle(true);
		return this;
	}
	
	/**
	 * Disable members folding. If members folding is disabled, nothing happen.
	 */
	public FoldingPreferencePage disableMembers() {
		new CheckBox(this, 4).toggle(false);
		return this;
	}
	
	/**
	 * Check whether imports folding is enabled or not.
	 * @return true if import folding is enabled, false otherwise
	 */
	public boolean isImportsChecked() {
		return new CheckBox(this, 5).isChecked();
	}

	/**
	 * Enable imports folding. If imports folding is enabled, nothing happen.
	 */
	public FoldingPreferencePage enableImports() {
		new CheckBox(this, 5).toggle(true);
		return this;
	}
	
	/**
	 * Disable imports folding. If imports folding is disabled, nothing happen.
	 */
	public FoldingPreferencePage disableImports() {
		new CheckBox(this, 5).toggle(false);
		return this;
	}

}
