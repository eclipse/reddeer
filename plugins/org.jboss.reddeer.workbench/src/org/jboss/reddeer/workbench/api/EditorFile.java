/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.workbench.api;

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
