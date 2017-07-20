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
package org.eclipse.reddeer.eclipse.datatools.connectivity.oda.flatfile.ui.wizards;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.text.DefaultText;

/**
 * A wizard page for creating new flat file data source profile.
 * 
 * @author apodhrad
 * 
 */
public class FolderSelectionWizardPage extends WizardPage {

	public static final String PROFILE_NAME = "Flat File Data Source";

	public static final String LABEL_HOME_FOLDER = "Select home folder:";
	public static final String LABEL_CHARSET = "Select charset:";
	public static final String LABEL_STYLE = "Select flatfile style:";
	public static final String LABEL_FISRT_LINE_NAME = "Use first line as column name indicator.";
	public static final String LABEL_SECOND_LINE_TYPE = "Use second line as data type indicator.";

	/**
	 * Instantiates a new connection profile flat file page.
	 */
	public FolderSelectionWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Sets the home folder.
	 *
	 * @param folder the new home folder
	 */
	public FolderSelectionWizardPage setHomeFolder(String folder) {
		new DefaultText(referencedComposite, 0).setText(folder);
		return this;
	}

	/**
	 * Sets the charset.
	 *
	 * @param charset the new charset
	 */
	public FolderSelectionWizardPage setCharset(String charset) {
		new LabeledCombo(referencedComposite, LABEL_CHARSET).setSelection(charset);
		return this;
	}

	/**
	 * Sets the style.
	 *
	 * @param style the new style
	 */
	public FolderSelectionWizardPage setStyle(String style) {
		new LabeledCombo(referencedComposite, LABEL_STYLE).setSelection(style);
		return this;
	}

	/**
	 * Use first line as name indicator.
	 *
	 * @param use the use
	 */
	public FolderSelectionWizardPage useFirstLineAsNameIndicator(boolean use) {
		useProperty(LABEL_FISRT_LINE_NAME, use);
		return this;
	}

	/**
	 * Use second line as type indicator.
	 *
	 * @param use the use
	 */
	public FolderSelectionWizardPage useSecondLineAsTypeIndicator(boolean use) {
		useProperty(LABEL_SECOND_LINE_TYPE, use);
		return this;
	}

	private FolderSelectionWizardPage useProperty(String property, boolean use) {
		new CheckBox(referencedComposite, "property").toggle(use);
		return this;
	}

}
