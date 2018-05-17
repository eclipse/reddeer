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
package org.eclipse.reddeer.eclipse.jdt.ui.javaeditor;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;

/**
 * Wait condition which return true if there is a given number of breakpoints.
 * You can specify whether all breakpoints should be considered or only
 * breakpoints associated to a given Java editor.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class NumberOfBreakpoints extends AbstractWaitCondition {

	private JavaEditor javaEditor;
	private int expectedNumberOfBreakpoints;

	/**
	 * Constructs a wait condition where all breakpoints are taken into account.
	 * 
	 * @param expectedNumberOfBreakpoints
	 *            expected number of breakpoints
	 */
	public NumberOfBreakpoints(int expectedNumberOfBreakpoints) {
		this(null, expectedNumberOfBreakpoints);
	}

	/**
	 * Constructs a wait condition where only breakpoints associated to a given Java
	 * editor are taken into account.
	 * 
	 * @param javaEditor
	 *            java editor
	 * @param expectedNumberOfBreakpoints
	 *            expected number of breakpoints
	 */
	public NumberOfBreakpoints(JavaEditor javaEditor, int expectedNumberOfBreakpoints) {
		this.javaEditor = javaEditor;
		this.expectedNumberOfBreakpoints = expectedNumberOfBreakpoints;
	}

	@Override
	public boolean test() {
		int actualNumberOfBreakpoints;
		if (javaEditor == null) {
			actualNumberOfBreakpoints = JavaEditorHandler.getInstance().getAllBreakpoints().length;
		} else {
			actualNumberOfBreakpoints = javaEditor.getBreakpoints().size();
		}
		return actualNumberOfBreakpoints == expectedNumberOfBreakpoints;
	}

}
