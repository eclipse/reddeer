/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
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

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.swt.graphics.Image;

/**
 * Interface with base operations which can be performed with workbench part.
 * 
 * @author Vlado Pakan
 */
public interface WorkbenchPart extends ReferencedComposite {
	
	/**
	 * Activates workbench part.
	 */
	void activate();

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
    
    /**
     * Returns ToolTip text of workbench part
     * @return TooTip text of workbench part
     */
    String getTitleToolTip();
    
    /**
     * Returns Title Image of workbench part
     * @return Title Image of workbench part
     */
    Image getTitleImage();
    
    /**
     * Checks if workbench part is active
     * @return true if workbench part is active, false otherwise
     */
    boolean isActive();
    
    /**
     * Minimize workbench part.
     */
	void minimize();
	
	/**
	 * Maximize workbench part.
	 */
	void maximize();
	
	/**
	 * Restore workbench part.
	 */
	void restore();

	/**
	 * Returns a context menu associated to the WorkbenchPart.
	 * 
	 * @return Context menu associated to the WorkbenchPart
	 */
	Menu getContextMenu();
}