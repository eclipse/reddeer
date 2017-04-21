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
package org.eclipse.reddeer.eclipse.jdt.ui.preferences;

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
	public FoldingPreferencePage() {
		super(new String[] {"Java", "Editor", "Folding"});
	}
	
	/**
	 * Check whether folding is enabled or not.
	 * 
	 * @return true if folding is enabled, false otherwise
	 */
	public boolean isFoldingChecked() {
		return new CheckBox(0).isChecked();
	}
	
	/**
	 * Enable folding. If folding is enabled, nothing happen.
	 */
	public void enableFolding() {
		new CheckBox(0).toggle(true);
	}
	
	/**
	 * Disable folding. If folding is disabled, nothing happen.
	 */
	public void disableFolding() {
		new CheckBox(0).toggle(false);
	}
	
	/**
	 * Check whether folding of comments is enabled or not.
	 * 
	 * @return true if folding of comments is enabled, false otherwise
	 */
	public boolean isCommentsChecked() {
		return new CheckBox(1).isChecked();
	}
	
	/**
	 * Enable comments folding. If comments folding is enabled, nothing happen.
	 */
	public void enableComments() {
		new CheckBox(1).toggle(true);
	}
	
	/**
	 * Disable comments folding. If comments folding is disabled, nothing happen.
	 */
	public void disableComments() {
		new CheckBox(1).toggle(false);
	}
	
	/**
	 * Check whether header comments are folding or not.
	 * @return true if header comments are folding, false otherwise
	 */
	public boolean isHeaderCommentsChecked() {
		return new CheckBox(2).isChecked();
	}
	
	/** 
	 * Enable header comments folding. If header comments folding is enabled, nothing happen.
	 */
	public void enableHeaderComments() {
		new CheckBox(2).toggle(true);
	}
	
	/**
	 * Disable header comments folding. If header comments folding is disabled, nothing happen.
	 */
	public void disableHeaderComments() {
		new CheckBox(2).toggle(false);
	}
	
	/**
	 * Check whether inner types are folding or not.
	 * @return true if inner types are folding, false otherwise
	 */
	public boolean isInnerTypesChecked() {
		return new CheckBox(3).isChecked();
	}

	/**
	 * Enable inner types folding. If inner types folding is enabled, nothing happen.
	 */
	public void enableInnerTypes() {
		new CheckBox(3).toggle(true);
	}
	
	/**
	 * Disable inner types folding. If inner types folding is disabled, nothing happen.
	 */
	public void disableInnerTypes() {
		new CheckBox(3).toggle(false);
	}
	
	/**
	 * Check whether members are folding or not.
	 * @return true if members are folding, false otherwise
	 */
	public boolean isMembersChecked() {
		return new CheckBox(4).isChecked();
	}

	/**
	 * Enable members folding. If members folding is enabled, nothing happen.
	 */
	public void enableMembers() {
		new CheckBox(4).toggle(true);
	}
	
	/**
	 * Disable members folding. If members folding is disabled, nothing happen.
	 */
	public void disableMembers() {
		new CheckBox(4).toggle(false);
	}
	
	/**
	 * Check whether imports folding is enabled or not.
	 * @return true if import folding is enabled, false otherwise
	 */
	public boolean isImportsChecked() {
		return new CheckBox(5).isChecked();
	}

	/**
	 * Enable imports folding. If imports folding is enabled, nothing happen.
	 */
	public void enableImports() {
		new CheckBox(5).toggle(true);
	}
	
	/**
	 * Disable imports folding. If imports folding is disabled, nothing happen.
	 */
	public void disableImports() {
		new CheckBox(5).toggle(false);
	}

}
