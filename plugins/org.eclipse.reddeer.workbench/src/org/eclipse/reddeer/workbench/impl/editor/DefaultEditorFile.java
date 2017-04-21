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
package org.eclipse.reddeer.workbench.impl.editor;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.reddeer.workbench.api.EditorFile;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;

/**
 * Basic implementation of EditorFile which represents a file associated with an
 * editor.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class DefaultEditorFile implements EditorFile {

	protected IFile iFile;

	/**
	 * Constructs the a implementation of EditorFile from IFile.
	 * 
	 * @param iFile
	 *            IFile
	 */
	public DefaultEditorFile(IFile iFile) {
		this.iFile = iFile;
	}

	@Override
	public InputStream getInputStream() {
		try {
			return iFile.getContents();
		} catch (Exception ce) {
			throw new WorkbenchLayerException("Cannot get an input stream from the editor file", ce);
		}
	}

	@Override
	public String getRelativePath() {
		return iFile.getFullPath().toString();
	}

	@Override
	public String getAbsolutePath() {
		return iFile.getLocation().toString();
	}

}
