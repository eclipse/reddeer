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
