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
package org.eclipse.reddeer.eclipse.datatools.sqltools.result.ui;

public enum SQLResultStatus {

	SUCCEEDED("Succeeded"), FAILED("Failed"), STARTED("Started");

	public String status;

	private SQLResultStatus(String status) {
		this.status = status;
	}

	/**
	 * From string.
	 *
	 * @param text the text
	 * @return the SQL result status
	 */
	public static SQLResultStatus fromString(String text) {
		if (text != null) {
			for (SQLResultStatus s : SQLResultStatus.values()) {
				if (text.equalsIgnoreCase(s.status)) {
					return s;
				}
			}
		}
		return null;
	}

}
