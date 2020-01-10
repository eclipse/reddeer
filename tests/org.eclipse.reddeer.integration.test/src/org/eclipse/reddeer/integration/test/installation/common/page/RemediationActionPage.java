/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.integration.test.installation.common.page;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.jface.dialogs.TitleAreaDialog;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;

public class RemediationActionPage extends ValidatedWizardPage{
	
	public static final String PAGE_TITLE = "(.*Remediation Page)|(Confirm Selected Features)";
	public static final String RADIO_KEEP_MY_INSTALL = "Keep my installation the same and modify the items being installed to be compatible";
	public static final String RADIO_UPDATE_MY_INSTALL = "Update my installation to be compatible with the items being installed";
	public static final String RADIO_SHOW_ORIGINAL_ERRORS = "Show original error and build my own solution:";
	
	public RemediationActionPage(TitleAreaDialog installDialog) {
		super(installDialog, new WithTextMatcher(new RegexMatcher(PAGE_TITLE)));
	}
	
	public void chooseKeepMyInstallation() {
		new RadioButton(referencedComposite, RADIO_KEEP_MY_INSTALL).click();
	}
	
	public void chooseUpdateMyInstallation() {
		new RadioButton(referencedComposite, RADIO_UPDATE_MY_INSTALL).click();
	}
	
	public void chooseShowOriginalErrors() {
		new RadioButton(referencedComposite, RADIO_SHOW_ORIGINAL_ERRORS).click();
	}
	
	public boolean isEnabledKeepMyInstallation() {
		return new RadioButton(referencedComposite, RADIO_KEEP_MY_INSTALL).isEnabled();
	}
	
	public boolean isEnabledUpdateMyInstallation() {
		return new RadioButton(referencedComposite, RADIO_UPDATE_MY_INSTALL).isEnabled();
	}
	
	public boolean isEnabledShowOriginalErrors() {
		return new RadioButton(referencedComposite, RADIO_SHOW_ORIGINAL_ERRORS).isEnabled();
	}
	
	public String getOriginalError() {
		chooseShowOriginalErrors();
		try {
			//Oxygen
			return new DefaultStyledText(referencedComposite).getText();
		} catch (CoreLayerException ex) {
			//Neon
			return new DefaultText(referencedComposite).getText();
		}		
	}
	
	public DefaultTree getSolutionsDetailsTree() {
		if (isEnabledUpdateMyInstallation() || isEnabledKeepMyInstallation()) {
			return new DefaultTree(this);
		}
		else {
			throw new IllegalStateException("Solution Details Tree is available only if either 'Keep...' or 'Update...' is selected.");
		}
	}
}
