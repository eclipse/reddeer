/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.core.handler;

import org.eclipse.swt.widgets.Shell;

/**
 * Interface used as a hook for method {@link ShellHandler#closeAllNonWorbenchShells()}.
 * 
 * @author Vlado Pakan
 *
 */
public interface IBeforeShellIsClosed {
	
	/**
	 * Method is called right before closing shell within method
	 * {@link ShellHandler#closeAllNonWorbenchShells(IBeforeShellIsClosed)}.
	 * 
	 * @param shell shell to close
	 */
	void runBeforeShellIsClosed(Shell shell);
}
