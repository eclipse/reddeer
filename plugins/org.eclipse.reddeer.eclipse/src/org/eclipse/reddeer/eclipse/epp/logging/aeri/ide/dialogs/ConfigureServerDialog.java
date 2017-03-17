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
package org.eclipse.reddeer.eclipse.epp.logging.aeri.ide.dialogs;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.jface.dialogs.TitleAreaDialog;
import org.eclipse.reddeer.jface.matcher.DialogWithTitleMatcher;
import org.eclipse.reddeer.swt.api.Group;
import org.eclipse.reddeer.swt.api.Text;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.group.DefaultGroup;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Represends AERI configure server dialog
 * 
 * @author rawagner
 *
 */
public class ConfigureServerDialog extends TitleAreaDialog {

	public ConfigureServerDialog() {
		super(new DialogWithTitleMatcher(new RegexMatcher("Configure Error Reporting for .*")));
	}

	public void enable() {
		new PushButton("Enable").click();
		new WaitWhile(new ShellIsAvailable(getShell()));
	}

	public void disable() {
		new PushButton("Disable").click();
		new WaitWhile(new ShellIsAvailable(getShell()));
	}

	public void setName(String name) {
		getNameWidget().setText(name);
	}

	public void setEmail(String email) {
		getEmailWidget().setText(email);
	}

	public String getName() {
		return getNameWidget().getText();
	}

	public String getEmail() {
		return getEmailWidget().getText();
	}

	public void anonymizePackageClassMethodNames(boolean toggle) {
		getAnonymizePackageWidget().toggle(toggle);
	}

	public boolean isAnonymizePackageClassMethodNames() {
		return getAnonymizePackageWidget().isChecked();
	}

	public void anonymizeErrorLogMessages(boolean toggle) {
		getAnonymizeErrorWidget().toggle(toggle);
	}

	public boolean isAnonymizeErrorLogMessages() {
		return getAnonymizeErrorWidget().isChecked();
	}

	protected Text getNameWidget() {
		return new LabeledText(getContactGroup(), "Name:");
	}

	protected Text getEmailWidget() {
		return new LabeledText(getContactGroup(), "E‐mail:");
	}

	protected Group getSendOptionsGroup() {
		return new DefaultGroup("Send Options");
	}

	protected Group getContactGroup() {
		return new DefaultGroup("Contact Information");
	}

	protected CheckBox getAnonymizePackageWidget() {
		return new CheckBox(getSendOptionsGroup(), "Anonymize package, class, and method names");
	}

	protected CheckBox getAnonymizeErrorWidget() {
		return new CheckBox(getSendOptionsGroup(), "Anonymize error log messages");
	}

}
