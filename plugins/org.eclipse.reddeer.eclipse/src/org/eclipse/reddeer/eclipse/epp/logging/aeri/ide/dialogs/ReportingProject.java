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

import org.eclipse.swt.graphics.Image;
import org.eclipse.reddeer.swt.api.Label;
import org.eclipse.reddeer.swt.api.Link;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.uiforms.api.Hyperlink;

/**
 * Represents aeri reporting project
 * 
 * @author rawagner
 *
 */
public class ReportingProject {

	private Label imageLabel;
	private Label projectName;
	private Link projectDescription;
	private Hyperlink projectLink;
	private Link configureLink;
	private CheckBox onofCheckbox;

	public ReportingProject(Label imageLabel, Label projectName, Link projectDescription, Hyperlink projectLink,
			Link configureLink, CheckBox onofCheckbox) {
		this.imageLabel = imageLabel;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.projectLink = projectLink;
		this.configureLink = configureLink;
		this.onofCheckbox = onofCheckbox;

	}

	public ConfigureServerDialog configure() {
		configureLink.click();
		return new ConfigureServerDialog();
	}

	public Hyperlink getProjectLink() {
		return projectLink;
	}

	public String getProjectName() {
		return projectName.getText();
	}

	public String getProjectDescription() {
		return projectDescription.getText();
	}

	public Image getProjectImage() {
		return imageLabel.getImage();
	}

	public void toggleEnable(boolean toggle) {
		onofCheckbox.toggle(toggle);
	}

	public boolean isEnabled() {
		return onofCheckbox.isChecked();
	}

}
