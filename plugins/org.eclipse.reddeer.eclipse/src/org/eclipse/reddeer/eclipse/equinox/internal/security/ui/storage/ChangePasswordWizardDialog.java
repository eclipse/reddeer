/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.reddeer.eclipse.equinox.internal.security.ui.storage;

import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.impl.button.NoButton;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.YesButton;
import org.eclipse.reddeer.swt.impl.group.DefaultGroup;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.DefaultText;

/**
 * Secure storage change master password dialog.
 *
 * @author jnovak@redhat.com
 */
public class ChangePasswordWizardDialog extends WizardDialog {

	private static final int HINTS_COUNT = 2;

	public ChangePasswordWizardDialog() {
		super("Change Password");
	}

	public void ignoreHints() {
		new DefaultShell("Secure Storage - Password Hint Needed");
		new NoButton().click();
	}

	public void fillHints(String questionOne, String questionTwo,
						  String answerOne, String answerTwo) {

		new DefaultShell("Secure Storage - Password Hint Needed");
		new YesButton().click();

		new DefaultShell("Password Recovery");

		setFirstHint(questionOne, answerOne);
		setSecondHint(questionTwo, answerTwo);
		new OkButton().click();
	}

	public void changeMasterPasswordWithoutHints(String oldPassword, String newPassword) {
		changeMasterPassword(oldPassword, newPassword, null, null);
	}

	public void changeMasterPasswordWithHints(String oldPassword, String newPassword,
					String questionOne, String questionTwo,
					String answerOne, String answerTwo) {

		changeMasterPassword(oldPassword, newPassword,
				new String[]{questionOne, questionTwo},
				new String[]{answerOne, answerTwo}
		);
	}

	private void setFirstHint(String question, String answer) {
		setHint(question, answer, "Question 1");
	}

	private void setSecondHint(String question, String answer) {
		setHint(question, answer, "Question 2");
	}

	private void setHint(String question, String answer, String groupLabel) {
		DefaultGroup group = new DefaultGroup(groupLabel);
		DefaultText questionField = new DefaultText(group, 0);
		DefaultText answerField = new DefaultText(group, 1);

		questionField.setText(question);
		answerField.setText(answer);
	}

	private void changeMasterPassword(String oldPassword, String newPassword,
									 String[] questions, String[] answers) {
		this.next();
		DescriptiveStorageLoginDialog oldPasswordDialog = new DescriptiveStorageLoginDialog();
		oldPasswordDialog.setPassword(oldPassword);
		oldPasswordDialog.ok();

		this.next();
		DescriptiveStorageNewDialog newPasswordDialog = new DescriptiveStorageNewDialog();
		newPasswordDialog.setNewPassword(newPassword);
		newPasswordDialog.ok();

		if (areHintsPresent(questions, answers)) {
			this.fillHints(questions[0], questions[1], answers[0], answers[1]);
		} else {
			this.ignoreHints();
		}

		this.finish();
	}

	private static boolean areHintsPresent(String[] questions, String[] answers) {
		return questions != null && answers != null
				&& questions.length == HINTS_COUNT && answers.length == HINTS_COUNT;
	}

}
