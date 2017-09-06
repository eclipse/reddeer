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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.handler.CompositeHandler;
import org.eclipse.reddeer.jface.condition.WindowIsAvailable;
import org.eclipse.reddeer.jface.dialogs.TitleAreaDialog;
import org.eclipse.reddeer.swt.api.Label;
import org.eclipse.reddeer.swt.api.Link;
import org.eclipse.reddeer.swt.api.ScrolledComposite;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.composite.DefaultScrolledComposite;
import org.eclipse.reddeer.swt.impl.label.DefaultLabel;
import org.eclipse.reddeer.swt.impl.link.DefaultLink;
import org.eclipse.reddeer.uiforms.api.Hyperlink;
import org.eclipse.reddeer.uiforms.impl.hyperlink.DefaultHyperlink;

/**
 * Represents AERI setup wizard
 * 
 * @author rawagner
 *
 */
public class SetupWizard extends TitleAreaDialog {

	private static final Logger log = Logger.getLogger(SetupWizard.class);

	public SetupWizard() {
		super("Configure Automated Error Reporting");
	}

	public List<ReportingProject> getProjects() {
		List<ReportingProject> reportingProjects = new ArrayList<>();
		ScrolledComposite sc = new DefaultScrolledComposite();
		final Composite scrolledContent = ((Composite) sc.getControl());
		Control[] projectSections = CompositeHandler.getInstance().getChildren(scrolledContent);
		if (projectSections != null) {
			for (Control projectSection : projectSections) {
				Control[] project = CompositeHandler.getInstance().getChildren((Composite) projectSection);
				Label projectIcon = new DefaultLabel((org.eclipse.swt.widgets.Label) project[0]);
				Label projectName = new DefaultLabel((org.eclipse.swt.widgets.Label) project[1]);
				Link projectDescription = new DefaultLink((org.eclipse.swt.widgets.Link) project[2]);
				Hyperlink projectHyperlink = new DefaultHyperlink((org.eclipse.ui.forms.widgets.Hyperlink) project[3]);
				Link configureLink = new DefaultLink((org.eclipse.swt.widgets.Link) project[4]);
				CheckBox onofCheckbox = new CheckBox((org.eclipse.swt.widgets.Button) project[5]);

				reportingProjects.add(new ReportingProject(projectIcon, projectName, projectDescription,
						projectHyperlink, configureLink, onofCheckbox));
			}
		}
		return reportingProjects;

	}

	public ReportingProject getProject(String projectName) {
		for (ReportingProject rp : getProjects()) {
			if (rp.getProjectName().equals(projectName)) {
				return rp;
			}
		}
		return null;
	}

	public void enable() {
		enable(true);
	}

	/**
	 * Enables reporting
	 * 
	 * @param waitForNonEclipseShell
	 *            true if we should wait for warning about sending reports to
	 *            non-eclipse projects, false otherwise
	 */
	public void enable(boolean waitForNonEclipseShell) {
		new PushButton("Enable").click();
		if (waitForNonEclipseShell) {
			try {
				ExternalServersEnabledAlert alertShell = new ExternalServersEnabledAlert();
				alertShell.ok();
			} catch (RedDeerException e) {
				log.debug("alert shell did not open");
			}
		}
		new WaitWhile(new WindowIsAvailable(this));
	}

	public void disable() {
		new PushButton("Disable").click();
		new WaitWhile(new WindowIsAvailable(this));
	}

}
