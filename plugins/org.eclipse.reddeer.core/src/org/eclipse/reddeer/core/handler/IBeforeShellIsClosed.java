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
package org.eclipse.reddeer.core.handler;

import org.eclipse.swt.widgets.Shell;

/**
 * Interface used as a hook for closing shells.
 * 
 * @author Vlado Pakan
 *
 */
public interface IBeforeShellIsClosed {
	
	/**
	 * Method is called right before closing shell.
	 * 
	 * @param shell shell to close
	 */
	void runBeforeShellIsClosed(Shell shell);
}
