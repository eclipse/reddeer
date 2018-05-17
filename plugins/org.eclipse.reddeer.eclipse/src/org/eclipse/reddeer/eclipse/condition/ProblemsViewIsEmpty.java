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
package org.eclipse.reddeer.eclipse.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;

/**
 * Wait condition for a empty problems view. If there are no errors nor warnings, condition is met.
 * 
 * @author mlabuda@redhat.com
 * since 0.7
 *
 */
public class ProblemsViewIsEmpty extends AbstractWaitCondition {

	private ProblemsView problemsView;
	
	/**
	 * Creates a new wait condition to wait for empty problems view.
	 */
	public ProblemsViewIsEmpty() {
		problemsView = new ProblemsView();
		problemsView.open();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		problemsView.activate();
		// using this should ensure atomicity, because getProblems method of Problems view 
		// does not get warnings and errors at once
		return new DefaultTree().getItems().isEmpty();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return " Problems view is empty.";
	}

}
