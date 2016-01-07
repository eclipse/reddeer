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
package org.jboss.reddeer.workbench.api;

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
     * Returns true of view is visible.
     *
     * @return true, if is visible
     */
    boolean isVisible();
    
    /**
     * Returns true of view is opened
     * View is available in workbench but not necessary visible.
     *
     * @return true, if is opened
     */
    boolean isOpened();
    
    /**
     * Returns true of view is active.
     *
     * @return true, if is active
     */
    boolean isActive();
}
