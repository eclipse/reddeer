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
package org.eclipse.reddeer.workbench.api;

/**
 * Interface with base operations which can be performed with workbench part.
 * 
 * @author Vlado Pakan
 */
public interface WorkbenchPart {
	
	/**
	 * Activates workbench part.
	 */
	void activate();

	/**
	 * Minimize workbench part.
	 */
	void minimize();

	/**
	 * Maximize workbench part.
	 */
	void maximize();

	/**
	 * Restores workbench part.
	 */
	void restore();

	/**
	 * Close workbench part.
	 */
	void close();
	
	/**
	 * Returns Title of workbench part.
	 *
	 * @return Title of the workbench part
	 */
    String getTitle();

}
