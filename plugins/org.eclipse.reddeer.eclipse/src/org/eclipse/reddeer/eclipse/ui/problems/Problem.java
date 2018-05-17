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
package org.eclipse.reddeer.eclipse.ui.problems;

import java.util.List;

import org.eclipse.reddeer.eclipse.ui.markers.AbstractMarker;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView;
import org.eclipse.reddeer.eclipse.ui.views.markers.AbstractMarkersSupportView.Column;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView.ProblemType;
import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Problem represents an error or warning in Problems view.
 * 
 * @author mlabuda@redhat.com
 * @author rawagner
 * @since 0.7
 */
public class Problem extends AbstractMarker {
	
	private ProblemType problemType;
		
	/**
	 * Creates a new problem of Problems view. 
	 * 
	 * @param problemType type of a problem [warning|error]
	 * @param item tree item of a problem
	 */
	public Problem(ProblemType problemType, TreeItem item) {
		super(item);
		this.problemType = problemType;
	}
	
	/**
	 * Constructs a new problem of specific problem type. 
	 * @param problemType type of a problem [warning|error]
	 * @param item tree item of a problem
	 */
	public Problem(String problemType, TreeItem item) {
		this(ProblemType.fromString(problemType), item);
	}
	
	/**
	 * Gets type of the problem. Currently either warning or error. There is also info but this feature is not 
	 * supported yet.
	 * 
	 * @return type of the problem
	 */
	public ProblemType getProblemType() {
		return problemType;
	}

	@Override
	protected String getCell(Column column) {
		ProblemsView problemsView = new ProblemsView();
		List<String> columns = problemsView.getProblemColumns();
		if (columns.contains(column.toString())) {
			return markerItem.getCell(problemsView.getIndexOfColumn(column));
		}
		return null;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
