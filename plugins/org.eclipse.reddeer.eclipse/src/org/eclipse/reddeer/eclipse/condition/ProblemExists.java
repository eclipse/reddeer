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
package org.eclipse.reddeer.eclipse.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.eclipse.ui.markers.matcher.AbstractMarkerMatcher;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView.ProblemType;

/**
 * Wait condition for existence of a problem. Condition wait for existence of a specific problem or problem type. 
 * If such problem or problem type is available in Problems view. condition is met.
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class ProblemExists extends AbstractWaitCondition {

	private ProblemType problemType;
	private AbstractMarkerMatcher[] matchers;
	private ProblemsView problemsView;
	
	/**
	 * Creates a new ProblemExists condition. If provided array of matchers is empty the wait condition is 
	 * met once there is a problem of specified problem type. If there are any markers matchers, a problem of 
	 * specified problem type has to match to those markers matchers.
	 * 
	 * @param <T> marker matcher type
	 * @param problemType type of a problem (warning or error)
	 * @param matchers matchers for a problem
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractMarkerMatcher> ProblemExists(ProblemType problemType, T... matchers) {
		this.problemType = problemType;
		this.matchers = matchers;
		problemsView = new ProblemsView();
		problemsView.open();
	}
	
	@Override
	public boolean test() {
		problemsView.activate();
		return !problemsView.getProblems(problemType, matchers).isEmpty();
	}

	@Override
	public String description() {
		return " problem of type " + problemType + " exists in Problems view.";
	}

}
