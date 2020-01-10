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
import org.eclipse.reddeer.integration.test.installation.common.page.RemediationActionPage;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.junit.rules.ErrorCollector;

public class RemediationActionValidator {

	private static Logger LOG = Logger.getLogger(RemediationActionValidator.class);

	/**
	 * Check offered solution and return true if remediation action is considered as
	 * acceptable. Remediation action is considered as acceptable if at least one
	 * item will be installed or upgraded and if no item will be skipped or
	 * uninstalled. If the solution is not acceptable the false will be returned and
	 * error will be added to the collector.
	 * 
	 * @param remetiationPage instance of Remediation Page
	 * @param collector JUnit ErrorCollector
	 * @return true if solution is acceptable, otherwise false
	 */
	public static boolean validateSolution(RemediationActionPage remetiationPage, ErrorCollector collector) {
		DefaultTree solutionTree = remetiationPage.getSolutionsDetailsTree();

		boolean uninstallSectionFound = false;
		boolean notInstallSectionFound = false;
		boolean installSectionFound = false;
		boolean upgradeSectionFound = false;
		StringBuilder sb = new StringBuilder("Solution details:\n");

		for (TreeItem item : solutionTree.getItems()) {
			sb.append(item.getCell(0) + "\n");

			for (TreeItem subItem : item.getItems()) {
				sb.append("-Name: " + subItem.getCell(0) + " Version: " + subItem.getCell(1) + " Id: "
						+ subItem.getCell(2) + "\n");
			}

			switch (item.getCell(0)) {
			case "Will be uninstalled":
				uninstallSectionFound = true;
				break;
			case "Will not be installed":
				notInstallSectionFound = true;
				break;
			case "Will be installed":
				installSectionFound = true;
				break;
			case "Will be upgraded/downgraded":
				upgradeSectionFound = true;
				break;
			}
		}

		LOG.debug(sb.toString());

		StringBuilder result = new StringBuilder();
		if (uninstallSectionFound) {
			result.append("-remediation action want to uninstall some plugins\n");
		}
		if (notInstallSectionFound) {
			result.append("-remediation action want to skip some plugins\n");
		}
		if (!installSectionFound && !upgradeSectionFound) {
			result.append("-remediation action doesn't want to install/update any plugins\n");
		}

		if (!result.toString().isEmpty()) {
			collector.addError(new AssertionError("Remediation action validation:\n" + result.toString()));
			return false;
		}

		return true;
	}

}
