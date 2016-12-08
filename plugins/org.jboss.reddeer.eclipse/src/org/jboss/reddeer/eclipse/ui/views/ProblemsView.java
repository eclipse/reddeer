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
package org.jboss.reddeer.eclipse.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.eclipse.condition.AbstractExtendedMarkersViewIsUpdating;
import org.jboss.reddeer.eclipse.ui.markers.matcher.AbstractMarkerMatcher;
import org.jboss.reddeer.eclipse.ui.problems.Problem;

/**
 * Represents the Problems view.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class ProblemsView extends AbstractMarkersSupportView {

	/**
	 * Constructs the view with "Problems".
	 */
	public ProblemsView() {
		super("Problems");
	}
	
	/**
	 * Returns a list of problems that are of a specific type or any and that are matching specified matchers.
	 * 
	 * @param problemType type of a problem
	 * @param matchers matchers of columns
	 * @return list of problem
	 */
	public List<Problem> getProblems(ProblemType problemType, AbstractMarkerMatcher... matchers) {
		activate();
		new WaitUntil(new ProblemsViewMarkerIsUpdating(),TimePeriod.SHORT,false);
		new WaitWhile(new ProblemsViewMarkerIsUpdating());
		
		List<Problem> result = new ArrayList<Problem>();
		if (problemType.equals(ProblemType.ERROR) || problemType.equals(ProblemType.ALL)) {
			result.addAll(getMarkers(Problem.class, ProblemType.ERROR.toString(), matchers));
		}
		if (problemType.equals(ProblemType.WARNING) || problemType.equals(ProblemType.ALL)) {
			result.addAll(getMarkers(Problem.class, ProblemType.WARNING.toString(), matchers));
		}
		return result;
	}
	

	
	/**
	 * Enum for type of a problem. Currently only warning and errors are supported although there is also info type. 
	 * 
	 * @author mlabuda@redhat.com
	 * @since 0.7
	 */
	public enum ProblemType {
		WARNING("Warnings"),
		ERROR("Errors"),
		ALL("ALL");
		
		String text;
		
		private ProblemType(String text) {
			this.text = text;
		}
		
		public String toString() {
			return text;
		}
		
		public static ProblemType fromString(String type) {
			if (type.equals("Warnings")) {
				return ProblemType.WARNING;
			} else {
				return ProblemType.ERROR;
			}
		}
	}
	
	/**
	 * Returns true if Problems view marker is updating its UI.
	 */
	@SuppressWarnings("restriction")
	private class ProblemsViewMarkerIsUpdating extends AbstractExtendedMarkersViewIsUpdating {

		public ProblemsViewMarkerIsUpdating() {
			super(ProblemsView.this, org.eclipse.ui.internal.views.markers.ProblemsView.class);
		}
	}
}
