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
package org.eclipse.reddeer.workbench.condition;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.workbench.api.Editor;
import org.eclipse.reddeer.workbench.impl.editor.Marker;

/**
 * Checks if specified editor contains validation markers
 * @author rawagner
 *
 */
public class EditorHasValidationMarkers extends AbstractWaitCondition {
	
	private Editor editor;
	private String type;
	private int line;
	private List<Marker> resultMarkers;
	
	/**
	 * Default constructor.
	 *
	 * @param editor which should be used to find validation markers
	 */
	public EditorHasValidationMarkers(Editor editor) {
		this(editor,null,-1);
	}
	
	/**
	 * Wait for marker with specified type.
	 *
	 * @param editor which should be used to find validation markers
	 * @param type of validation marker to find
	 */
	public EditorHasValidationMarkers(Editor editor, String type) {
		this(editor, type, -1);
	}
	
	/**
	 * Wait for marker on specified line.
	 *
	 * @param editor which should be used to find validation markers
	 * @param line on which marker should be found
	 */
	public EditorHasValidationMarkers(Editor editor, int line) {
		this(editor, null, line);
	}
	
	/**
	 * Wait for marker with specified type and on specified line.
	 *
	 * @param editor which should be used to find validation markers
	 * @param type of validation marker to find
	 * @param line on which marker should be found
	 */
	public EditorHasValidationMarkers(Editor editor, String type, int line) {
		this.editor = editor;
		this.type = type;
		this.line = line;
		this.resultMarkers = new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		editor.activate();
		List<Marker> markers = editor.getMarkers();
		boolean toReturn = false;
		if(type == null){
			if(line == -1){
				this.resultMarkers = markers;
				toReturn = editor.getMarkers().size() > 0;
			} else {
				for(Marker m: markers){
					if(m.getLineNumber() == line){
						this.resultMarkers.add(m);
						toReturn = true;
						break;
					}
				}
			}
		} else {
			if(line == -1){
				for(Marker m: markers){
					if(m.getType().equals(type)){
						this.resultMarkers.add(m);
						toReturn = true;
						break;
					}
				}
			} else {
				for(Marker m: markers){
					if(m.getType().equals(type) && m.getLineNumber() == line){
						this.resultMarkers.add(m);
						toReturn = true;
						break;
					}
				}
			}
		}
		return toReturn;
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		String description;
		if(type == null){
			if(line == -1){
				description = "Editor "+editor.getTitle()+" has validation markers";
			} else {
				description = "Editor "+editor.getTitle()+" has validation markers at line "+line;
			}
		} else {
			if(line == -1){
				description = "Editor "+editor.getTitle()+" has validation markers with type "+type;
			} else {
				description = "Editor "+editor.getTitle()+" has validation markers with type"+type+" at line "+line;
			}
		}
		return description;
	}

	@SuppressWarnings("unchecked")
	@Override 
	public List<Marker> getResult() {
		return this.resultMarkers.size() == 0 ? null : this.resultMarkers;
	}
	
}
