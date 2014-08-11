package org.jboss.reddeer.workbench.api;

import java.util.List;

import org.jboss.reddeer.jface.text.contentassist.ContentAssistant;
import org.jboss.reddeer.workbench.impl.editor.Marker;

/**
 * Interface with base operations which can be performed with editor.
 * @author rhopp
 * @author rawagner
 */
public interface Editor extends WorkbenchPart {

    /**
     * Returns editor title.
     * @return editor title
     */
    String getTitle();

    /**
     * Returns editor title tooltip.
     * @return editor title tooltip
     */
    String getTitleToolTip();

    /**
     * Checks if editor is dirty.
     * @return true if editor is dirty
     */
    boolean isDirty();

    /**
     * Tries to perform save on this editor.
     */
    void save();

    /**
     * Closes this editor.
     * @param save If true, content will be saved
     */
    void close(boolean save);

    /**
     * Closes all editors.
     * @param save If true, content will be saved
     */
    void closeAll(boolean save); 

    /**
     * Checks if editor is active.
     * @return whether is this editor currently active and has focus.
     */
    boolean isActive();

    /**
     * Opens content assistant.
     * @return Content assistant shell
     */
    ContentAssistant openContentAssistant();

    /**
     * Opens quickfix content assistant.
     * @return Content assistant shell
     */
    ContentAssistant openQuickFixContentAssistant();

    /**
     * Opens open on assistant.
     * @return Content assistant shell
     */
    ContentAssistant openOpenOnAssistant();

    /**
     * Returns editor validation markers.
     * @return editor validation markers
     */
    List<Marker> getMarkers();

}
