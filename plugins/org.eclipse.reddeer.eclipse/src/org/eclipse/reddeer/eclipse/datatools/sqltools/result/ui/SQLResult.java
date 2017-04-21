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

/**
 * SQLResult reporesents Result from executed SQL Query.
 * @author Jiri Peterka
 *
 */
public class SQLResult {

	SQLResultStatus status ;
	String operation;
	String date;
	String connectionProfile;
	
	
	/**
	 * Creates SQL Result instance.
	 *
	 * @param status SQL status
	 * @param operation SQL operation
	 * @param date SQL execution datetime
	 * @param connectionProfile SQL connection profile
	 */
	public SQLResult(String status, String operation, String date, String connectionProfile) {
		this.status = SQLResultStatus.fromString(status);
		this.operation = operation;
		this.date = date;
		this.connectionProfile = connectionProfile;
	}
	
	/**
	 * Returns SQL Result status. 
	 *
	 * @return the status
	 */
	public SQLResultStatus getStatus() {
		return status;
	}
	
	/**
	 * SQL Result operation.
	 *
	 * @return SQL result operation
	 */
	public String getOperation() {
		return operation;
	}
	
	/**
	 * SQL result execution datetime.
	 * @return sql result execution datetime
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * Provides SQL Connection profile.
	 * @return sql connection profile
	 */
	public String getConnectionProfile() {
		return connectionProfile;
	}
}
