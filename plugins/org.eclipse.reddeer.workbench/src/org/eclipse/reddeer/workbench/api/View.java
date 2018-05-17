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

/**
 * Interface with base operations which can be performed with view.
 * @author rawagner
 * 
 */
public interface View extends WorkbenchPart {

    /**
     * Open view.
     */
    void open();
    
    /**
     * Returns true of view is open
     * View is available in workbench but not necessary visible.
     *
     * @return true, if is opened
     */
    boolean isOpen();
    
}
