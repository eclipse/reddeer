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
