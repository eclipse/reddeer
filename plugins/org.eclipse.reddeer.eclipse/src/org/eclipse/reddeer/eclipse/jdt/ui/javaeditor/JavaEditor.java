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
package org.eclipse.reddeer.eclipse.jdt.ui.javaeditor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
import org.hamcrest.Matcher;

/**
 * Represents Java editor.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class JavaEditor extends TextEditor {

	/**
	 * Initializes currently focused Java Editor.
	 */
	public JavaEditor() {
		super();
	}

	/**
	 * Initializes editor with a given title.
	 * 
	 * @param title
	 *            title of Java editor
	 */
	public JavaEditor(String title) {
		super(title);
	}

	/**
	 * Initializes editor with a given title matcher.
	 *
	 * @param titleMatcher
	 *            title matcher of desired editor
	 */
	public JavaEditor(Matcher<String> titleMatcher) {
		super(titleMatcher);
	}

	/**
	 * Toggles a breakpoint at a given line number. Line numbering starts from 0. If
	 * a breakpoint already exists the the given line number then the breakpoint is
	 * removed and vice versa.
	 * 
	 * @param lineNumber
	 *            line number
	 */
	public void toggleBreakpoint(int lineNumber) {
		JavaEditorHandler.getInstance().toggleBreakpoint(getEditorPart(), lineNumber);
	}

	/**
	 * Adds a breakpoint at a given line number.Line numbering starts from 0.
	 * 
	 * @param lineNumber
	 *            line number
	 */
	public void addBreakpoint(int lineNumber) {
		if (getBreakpoint(lineNumber) == null) {
			int currentNumberOfBreakpoints = getBreakpoints().size();
			toggleBreakpoint(lineNumber);
			new WaitUntil(new NumberOfBreakpoints(currentNumberOfBreakpoints + 1));
		}
	}

	/**
	 * Removes a breakpoint at a given line number. Line numbering starts from 0.
	 * 
	 * @param lineNumber
	 *            line number
	 */
	public void removeBreakpoint(int lineNumber) {
		if (getBreakpoint(lineNumber) != null) {
			int currentNumberOfBreakpoints = getBreakpoints().size();
			toggleBreakpoint(lineNumber);
			new WaitUntil(new NumberOfBreakpoints(currentNumberOfBreakpoints - 1));
		}
	}

	/**
	 * Returns a breakpoint at a given line number. Line numbering starts from 0.
	 * 
	 * @param lineNumber
	 *            line number
	 * @return breakpoint at the line number
	 */
	public String getBreakpoint(int lineNumber) {
		IBreakpoint breakpoint = JavaEditorHandler.getInstance().getBreakpoint(getEditorPart(), lineNumber);
		if (breakpoint == null) {
			return null;
		}
		return getBreakpointLabel(breakpoint);
	}

	/**
	 * Returns a list of breakpoints associated to this Java editor.
	 * 
	 * @return list of breakpoints associated to the Java editor
	 */
	public List<String> getBreakpoints() {
		List<String> breakpoints = new ArrayList<>();
		for (IBreakpoint breakpoint : JavaEditorHandler.getInstance().getBreakpoints(getEditorPart())) {
			breakpoints.add(getBreakpointLabel(breakpoint));
		}
		return breakpoints;
	}

	/**
	 * Returns a label of a given breakpoint. The label is taken from the
	 * breakpoint's attribute 'message' without the type.
	 * 
	 * @param breakpoint
	 *            breakpoint
	 * @return label of the breakpoint
	 */
	private static String getBreakpointLabel(IBreakpoint breakpoint) {
		try {
			String message = breakpoint.getMarker().getAttribute("message").toString();
			if (message != null && message.contains(":")) {
				return message.split(":", 2)[1];
			} else {
				throw new RedDeerException("Cannot parse breakpoint's message '" + message + "'.");
			}
		} catch (CoreException e) {
			throw new RedDeerException("Cannot get breakpoint's attribute 'message'.");
		}
	}

}
