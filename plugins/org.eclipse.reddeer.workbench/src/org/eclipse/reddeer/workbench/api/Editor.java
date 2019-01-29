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

import java.util.List;

import org.eclipse.reddeer.jface.text.contentassist.ContentAssistant;
import org.eclipse.reddeer.workbench.impl.editor.AbstractEditor.ContentAssistantEnum;
import org.eclipse.reddeer.workbench.impl.editor.Marker;

/**
 * Interface with base operations which can be performed with editor.
 * 
 * @author rhopp
 * @author rawagner
 */
public interface Editor extends WorkbenchPart {

	/**
	 * Checks if editor is dirty.
	 * 
	 * @return true if editor is dirty
	 */
	boolean isDirty();

	/**
	 * Tries to perform save on this editor.
	 */
	void save();

	/**
	 * Closes this editor.
	 * 
	 * @param save
	 *            If true, content will be saved
	 */
	void close(boolean save);

	/**
	 * Opens content assistant.
	 * 
	 * @return Content assistant shell
	 */
	ContentAssistant openContentAssistant();

	/**
	 * Opens content assistant of specified type
	 * 
	 * @param assistantType
	 *            type of content assistant to open
	 * @return Content assistant shell
	 */
	ContentAssistant openContentAssistant(ContentAssistantEnum assistantType);

	/**
	 * Opens content assistant of specified type
	 * 
	 * @param assistantLabel
	 *            type of content assistant to open
	 * @return Content assistant shell
	 */
	ContentAssistant openContentAssistant(String assistantLabel);

	/**
	 * Retrieves callable content assistants.
	 * 
	 * @return List of assistants enums.
	 */
	List<ContentAssistantEnum> getAvailableContentAssistants();

	/**
	 * Opens quickfix content assistant.
	 * 
	 * @return Content assistant shell
	 */
	ContentAssistant openQuickFixContentAssistant();

	/**
	 * Opens open on assistant.
	 * 
	 * @return Content assistant shell
	 */
	ContentAssistant openOpenOnAssistant();

	/**
	 * Returns editor validation markers.
	 * 
	 * @return editor validation markers
	 */
	List<Marker> getMarkers();

	/**
	 * Returns editor validation AYT (as you type) markers.
	 * 
	 * @return editor validation AYT (as you type) markers
	 */
	List<Marker> getAYTMarkers();

	/**
	 * Gets content assistant opened automatically by instructions defined within run method of execute parameter or
	 * null in case Content Assistant shell was not opened.
	 *
	 * @param execute
	 *            the execute
	 * @return Content assistant
	 */
	ContentAssistant getAutoContentAssistant(Runnable execute);

	/**
	 * Returns an editor file associated to the editor.
	 * 
	 * @return Editor file associated to the editor
	 */
	EditorFile getAssociatedFile();
}