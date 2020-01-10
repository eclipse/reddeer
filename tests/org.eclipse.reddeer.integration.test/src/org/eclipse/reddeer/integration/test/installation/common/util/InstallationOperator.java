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

package org.eclipse.reddeer.integration.test.installation.common.util;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.integration.test.installation.common.dialog.CertificatesDialog;
import org.eclipse.reddeer.integration.test.installation.common.dialog.SecurityWarningDialog;
import org.eclipse.reddeer.integration.test.installation.common.dialog.SelectionNeededDialog;
import org.eclipse.reddeer.integration.test.installation.common.dialog.SoftwareUpdateDialog;
import org.eclipse.reddeer.integration.test.installation.common.page.InstallDetailsPage;
import org.eclipse.reddeer.integration.test.installation.common.page.RemediationActionPage;
import org.eclipse.reddeer.integration.test.installation.common.page.ReviewLicensesPage;
import org.eclipse.reddeer.integration.test.installation.common.page.UpdateDetailsPage;
import org.eclipse.reddeer.integration.test.installation.common.wait.WaitForOneOfShells;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.junit.screenshot.CaptureScreenshotException;
import org.eclipse.reddeer.junit.screenshot.ScreenshotCapturer;
import org.eclipse.reddeer.workbench.handler.WorkbenchShellHandler;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;

public class InstallationOperator {

	private static Logger LOG = Logger.getLogger(InstallationOperator.class);

	public static final int INSTALLATION_TIMEOUT = 3600;

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	private WizardDialog installWizard;

 	public InstallationOperator(WizardDialog installWizard, ErrorCollector collector) {
		this.installWizard = installWizard;
		this.collector = collector;
	}

	public void completeInstallation() {
		if (new WithTextMatcher(new RegexMatcher(RemediationActionPage.PAGE_TITLE)).matches(installWizard.getTitle())) {
			dealWithRemediationPage();
		}

		if (installWizard.getTitle().equals(InstallDetailsPage.PAGE_TITLE)) {
			dealWithInstallationDetailsPage();
		}

		if (installWizard.getTitle().equals(ReviewLicensesPage.PAGE_TITLE)) {
			dealWithReviewLicensesPage(true);
		}

		new WaitForOneOfShells(
				SecurityWarningDialog.HEADER, 
				SelectionNeededDialog.HEADER,
				SoftwareUpdateDialog.HEADER, 
				SelectionNeededDialog.EMPTY_HEADER, 
				CertificatesDialog.HEADER);
		
		if (SecurityWarningDialog.isAvailable()) {
			dealWithSecurityWarningWindow();
		}
		
		if (SelectionNeededDialog.isAvailable(true)) {
			dealWithSelectionNeededWindow(true);
		}

		if (CertificatesDialog.isAvailable()) {
			dealWithCertificatesWindow();
		}
		
		new WaitForOneOfShells(SelectionNeededDialog.HEADER, SoftwareUpdateDialog.HEADER, SelectionNeededDialog.EMPTY_HEADER);

		if (SelectionNeededDialog.isAvailable()) {
			dealWithSelectionNeededWindow();
		}
		
		if (SelectionNeededDialog.isAvailable(true)) {
			dealWithSelectionNeededWindow(true);
		}
		
		new WaitForOneOfShells(SoftwareUpdateDialog.HEADER, SelectionNeededDialog.EMPTY_HEADER);

		if (SelectionNeededDialog.isAvailable(true)) {
			dealWithSelectionNeededWindow(true);
		}
		
		new SoftwareUpdateDialog().no();
	}

	public void updateDryRun() {
		if (new WithTextMatcher(new RegexMatcher(RemediationActionPage.PAGE_TITLE)).matches(installWizard.getTitle())) {
			dealWithRemediationPage();
		}

		if (installWizard.getTitle().equals(UpdateDetailsPage.PAGE_TITLE)) {
			dealWithUpdateDetailsPage();
		} else {
			collector.addError(new AssertionError("Update Details page hasn't been displayed."));
		}

		if (installWizard.getTitle().equals(ReviewLicensesPage.PAGE_TITLE)) {
			dealWithReviewLicensesPage(false);
		}

		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells();
	}

	public void dealWithInstallationDetailsPage() {
		capture("Install_Details_Page");
		
		if (installWizard.isNextEnabled()) {
			installWizard.next();
		} else {
			installWizard.finish(TimePeriod.getCustom(INSTALLATION_TIMEOUT));
		}
	}
	
	public void dealWithUpdateDetailsPage() {
		capture("Update_Details_Page");
		
		if (installWizard.isNextEnabled()) {
			installWizard.next();
		}
	}

	public void dealWithReviewLicensesPage(boolean clickFinish) {
		capture("Review_Licenses_Page");

		ReviewLicensesPage licencePage = new ReviewLicensesPage(installWizard);
		collector.checkThat("Licence is accepted by default", licencePage.isLicenseAccepted(),
				new IsEqual<Boolean>(false));
		collector.checkThat("Finish button is enabled without accepting the licence",
				installWizard.isFinishEnabled(), new IsEqual<Boolean>(false));

		licencePage.acceptLicense();

		collector.checkThat("Finish button is not enabled by accepting the licence",
				installWizard.isFinishEnabled(), new IsEqual<Boolean>(true));

		if (clickFinish) {
			installWizard.finish(TimePeriod.getCustom(INSTALLATION_TIMEOUT));
		}
	}
	
	public void dealWithSecurityWarningWindow() {
		collector.addError(new AssertionError("Security Warning window has been displayed."));
		capture("Security_Warning_Window");

		new SecurityWarningDialog().ok();
	}

	public void dealWithSelectionNeededWindow() {
		dealWithSelectionNeededWindow(false);
	}
	
	public void dealWithSelectionNeededWindow(boolean emptyHeader) {
		LOG.warn("Selection Needed window has been displayed.");
		if(emptyHeader) {
			capture("Selection_Needed_Window_No_Header");
			new SelectionNeededDialog(SelectionNeededDialog.EMPTY_HEADER).acceptAll();
		}else {
			capture("Selection_Needed_Window");
			new SelectionNeededDialog().acceptAll();	
		}
	}
	
	public void dealWithCertificatesWindow() {
		LOG.warn("Selection Needed window has been displayed.");
		capture("Certificates_Window");
		new CertificatesDialog().acceptAll();
	}

	public void dealWithRemediationPage() {
		LOG.warn("Remediation Action page has been displayed.");

		RemediationActionPage remetiationPage = new RemediationActionPage(installWizard);

		if (remetiationPage.isEnabledShowOriginalErrors()) {
			remetiationPage.chooseShowOriginalErrors();
			capture("Remediation_Page_Show_Original_Errors");
			LOG.warn("Remediation Action needed. Original error:\n" + remetiationPage.getOriginalError());
		}

		if (remetiationPage.isEnabledKeepMyInstallation()) {
			remetiationPage.chooseKeepMyInstallation();
			capture("Remediation_Page_Keep_My_Installation");
		}

		if (remetiationPage.isEnabledUpdateMyInstallation()) {
			remetiationPage.chooseUpdateMyInstallation();
			capture("Remediation_Page_Update_My_Installation");
			RemediationActionValidator.validateSolution(remetiationPage, collector);
		} else {
			//collector.addError(new AssertionError("Remediation Action is needed and 'Update' choice is not available."));
			LOG.warn("Remediation Action is needed and 'Update' choice is not available.");
		}

		installWizard.next();
	}
	
	private void capture(String description) {
		try {
			ScreenshotCapturer.getInstance().captureScreenshot(getClass().getName() + description);
		} catch (CaptureScreenshotException e) {
			e.printInfo(LOG);
		}
	}
}
