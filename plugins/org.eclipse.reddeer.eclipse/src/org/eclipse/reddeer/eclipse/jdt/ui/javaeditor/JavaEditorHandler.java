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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetExtension;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.workbench.handler.TextEditorHandler;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Handler for Java editor.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class JavaEditorHandler extends TextEditorHandler {

	private static JavaEditorHandler instance;

	/**
	 * Returns an instance of DebugHandler.
	 * 
	 * @return instance of WorkbenchPartHandler
	 */
	public static JavaEditorHandler getInstance() {
		if (instance == null) {
			instance = new JavaEditorHandler();
		}
		return instance;
	}

	/**
	 * Returns a toggle breakpoint adapter for a given text editor.
	 * 
	 * @param textEditor
	 *            text editor
	 * @return
	 */
	public IToggleBreakpointsTargetExtension getToggleBreakpointAdapter(ITextEditor textEditor) {
		return (IToggleBreakpointsTargetExtension) DebugPlugin.getAdapter(textEditor, IToggleBreakpointsTarget.class);
	}

	/**
	 * Toggles a breakpoint in a given text editor at the specified line number. The
	 * line numbering starts from 0.
	 * 
	 * @param textEditor
	 *            text editor
	 * @param lineNumber
	 *            line number
	 */
	public void toggleBreakpoint(ITextEditor textEditor, int lineNumber) {
		if (lineNumber < 0) {
			throw new IllegalArgumentException("The line number cannot be negative.");
		}
		selectLine(textEditor, lineNumber);
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				ISelection selection = textEditor.getEditorSite().getSelectionProvider().getSelection();
				try {
					getToggleBreakpointAdapter(textEditor).toggleBreakpoints(textEditor, selection);
				} catch (CoreException e) {
					throw new RedDeerException("Cannot toggle a breakpoint.");
				}
			}
		});
	}

	/**
	 * Returns a list of breakpoints associated to a given text editor.
	 * 
	 * @param textEditor
	 *            text editor
	 * @return list of breakpoints associated to the editor
	 */
	public List<IBreakpoint> getBreakpoints(ITextEditor textEditor) {
		return getBreakpoints(textEditor, -1);
	}

	/**
	 * Returns a list of breakpoints associated to a given text editor at the
	 * specified line number (if the line number >= 0).
	 * 
	 * @param textEditor
	 *            text editor
	 * @param lineNumber
	 *            line number
	 * @return list of breakpoints associated to the editor at the specified line
	 *         number (if the line number >= 0)
	 */
	public List<IBreakpoint> getBreakpoints(ITextEditor textEditor, int lineNumber) {
		List<IBreakpoint> breakpoints = new ArrayList<>();
		IResource resource = ResourceUtil.getResource(textEditor.getEditorInput());
		for (IBreakpoint breakpoint : getAllBreakpoints()) {
			if (breakpoint.getMarker().getResource().equals(resource)) {
				if (lineNumber >= 0) {
					int offset;
					try {
						offset = Integer.parseInt(breakpoint.getMarker().getAttribute("charStart").toString());
					} catch (CoreException e) {
						throw new RedDeerException("Cannot get breakpoint's attribute 'charStart'.");
					}
					if (lineNumber == getLineOfOffest(textEditor, offset)) {
						breakpoints.add(breakpoint);
					}
				} else {
					breakpoints.add(breakpoint);
				}
			}
		}
		return breakpoints;
	}

	/**
	 * Returns a breakpoint associated to a given text editor at the specified line
	 * number.
	 * 
	 * @param textEditor
	 *            text editor
	 * @param lineNumber
	 *            line number
	 * @return
	 */
	public IBreakpoint getBreakpoint(ITextEditor textEditor, int lineNumber) {
		if (lineNumber < 0) {
			throw new IllegalArgumentException("The line number cannot be negative.");
		}
		List<IBreakpoint> breakpoints = getBreakpoints(textEditor, lineNumber);
		return breakpoints.isEmpty() ? null : breakpoints.get(0);
	}

	public IBreakpoint[] getAllBreakpoints() {
		return Display.syncExec(new ResultRunnable<IBreakpoint[]>() {

			@Override
			public IBreakpoint[] run() {
				return DebugPlugin.getDefault().getBreakpointManager().getBreakpoints();
			}
		});
	}

}
