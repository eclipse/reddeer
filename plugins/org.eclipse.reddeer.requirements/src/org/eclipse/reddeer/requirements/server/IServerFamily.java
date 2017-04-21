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
package org.eclipse.reddeer.requirements.server;

/**
 * 
 * @author Pavol Srna
 *
 */
public interface IServerFamily {
	
	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory();
	
	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel();
	
	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion();
}
