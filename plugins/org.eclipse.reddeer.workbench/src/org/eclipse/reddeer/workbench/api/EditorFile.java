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
package org.eclipse.reddeer.workbench.api;

import java.io.InputStream;

/**
 * API for a file associated to an editor.
 * 
 * @author apodhrad
 *
 */
public interface EditorFile {

	/**
	 * Returns an open input stream on the contents of this file. The client is
	 * responsible for closing the stream when finished.
	 * 
	 * @return An open input stream
	 */
	InputStream getInputStream();

	/**
	 * Returns a path relative to the workspace.
	 * 
	 * @return Relative path
	 */
	String getRelativePath();

	/**
	 * Returns an absolute path.
	 * 
	 * @return Absolute path
	 */
	String getAbsolutePath();

}
