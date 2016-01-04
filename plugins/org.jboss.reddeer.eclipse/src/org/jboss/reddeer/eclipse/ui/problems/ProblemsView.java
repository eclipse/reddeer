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
package org.jboss.reddeer.eclipse.ui.problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.eclipse.condition.AbstractExtendedMarkersViewIsUpdating;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.ui.problems.matcher.AbstractProblemMatcher;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ViewMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Represents the Problems view.
 *
 */
public class ProblemsView extends WorkbenchView{

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
	public List<Problem> getProblems(ProblemType problemType, AbstractProblemMatcher... matchers) {
		activate();
		new WaitUntil(new ProblemsViewMarkerIsUpdating(),TimePeriod.SHORT,false);
		new WaitWhile(new ProblemsViewMarkerIsUpdating());
		DefaultTree tree = new DefaultTree();
		return filterProblems(problemType, tree.getItems(), matchers);
	}
	
	/**
	 * Gets tree items representing problems of a specific type.
	 */
	private List<TreeItem> filterProblemType(List<TreeItem> list, ProblemType problemType){
		for (TreeItem problemSeverityTreeItem : list) {
			try{
				if (problemSeverityTreeItem.getText().matches("^Errors \\(\\d+ item.*\\)") 
						&& problemType == ProblemType.ERROR) {
					return problemSeverityTreeItem.getItems();
				} 
				if (problemSeverityTreeItem.getText().matches("^Warnings \\(\\d+ item.*\\)")
						&& problemType == ProblemType.WARNING) {
					return problemSeverityTreeItem.getItems();
				}
			} catch (CoreLayerException ex){
				//if widget is disposed we can ignore it - problem disappeared
				if(!problemSeverityTreeItem.isDisposed()){
					throw ex;
				}
			}
		}
		return new LinkedList<TreeItem>();
	}
	
	/**
	 * Filter problems of any type. You can use warnings, errors or any.
	 */
	private List<Problem> filterProblems(ProblemType problemType, List<TreeItem> items, AbstractProblemMatcher... matchers) {
		List<Problem> filteredResult = new ArrayList<Problem>();
		if (problemType == ProblemType.ERROR || problemType == ProblemType.ANY) {
			filteredResult.addAll(filterSpecificProblems(ProblemType.ERROR, items, matchers));
		}
		if (problemType == ProblemType.WARNING || problemType == ProblemType.ANY) {
			filteredResult.addAll(filterSpecificProblems(ProblemType.WARNING, items, matchers));
		}
		return filteredResult;
	}
	
	/**
	 * Filters problems of a specific type. Problem type used in calling this method should be either error or warning. DO NOT USE ANY.
	 */
	private List<Problem> filterSpecificProblems(ProblemType problemType, List<TreeItem> items, AbstractProblemMatcher... matchers) {
		List<Problem> problems = new ArrayList<Problem>();
		// get a specific problem type - either warnings or errors
		List<TreeItem> filteredItems = filterProblemType(items, problemType);
		for (TreeItem item: filteredItems) {
			boolean itemFitsMatchers = true;
			if(matchers != null){
				for (AbstractProblemMatcher matcher: matchers) {
					try{
						if (!matcher.matches(item.getCell(getIndexOfColumn(matcher.getColumn())))) {
							itemFitsMatchers = false;
							break;
						}
					} catch (CoreLayerException ex){
						//if widget is disposed we can ignore it - problem disappeared
						if(!item.isDisposed()){
							throw ex;
						}
					}
				}
			}
			if (itemFitsMatchers) {
				Problem problem = new Problem(problemType, item);
				problems.add(problem);
			}
		}
		return problems;
	}
	
	/**
	 * Gets columns in problems view.
	 * 
	 * @return list of columns in a tree of problems view
	 */
	public List<String> getProblemColumns() {
		activate();
		return new DefaultTree().getHeaderColumns();
	}
	
	/**
	 * Shows specified columns in a tree of problems view. 
	 * If one of columns is already shown, nothing happens for that one,
	 * others are shows as expected.
	 * 
	 * @param columns columns to show in a tree of problems view
	 */
	public void showProblemColumns(Column... columns) {
		String[] columnsToShow = getColumnsToShow(columns);
		if (columnsToShow.length > 0) {
			openConfigureColumnsShell();
			
			new DefaultTable(0).select(columnsToShow);
			new PushButton("Show ->").click();
			
			confirmChangesAndCloseConfigureColumnsShell();
		}
	}
	
	/**
	 * Hides specified column in a tree of problems view.
	 * If one of columns is already hidden, nothing happens for that one,
	 * other are hidden as expected.
	 * 
	 * @param columns columns to hide in a tree of problems view 
	 */
	public void hideProblemColumn(Column... columns) {
		String[] columnsToHide = getColumnsToHide(columns);
		if (columnsToHide.length > 0) {
			openConfigureColumnsShell();
			
			new DefaultTable(1).select(columnsToHide);
			new PushButton("<- Hide ").click();
			
			confirmChangesAndCloseConfigureColumnsShell();
		}
	}
	
	private String[] getColumnsToHide(Column[] columns) {
		List<String> visibleColumns = getProblemColumns();
		List<String> columnsToHide = new ArrayList<String>();
		
		if (columns != null && columns.length > 0) {
			for (Column column: columns) {
				if (visibleColumns.contains(column.toString())) {
					columnsToHide.add(column.toString());
				}
			}
		}
		
		return columnsToHide.toArray(new String[columnsToHide.size()]);
	}
	
	private String[] getColumnsToShow(Column[] columns) {
		List<String> visibleColumns = getProblemColumns();
		List<String> columnsToShow = new ArrayList<String>();
		

		if (columns != null && columns.length > 0) {
			for (Column column: columns) {
				if (!visibleColumns.contains(column.toString())) {
					columnsToShow.add(column.toString());
				}
			}
		}
		
		return columnsToShow.toArray(new String[columnsToShow.size()]);
	}
	
	/**
	 * Shows default problem columns for a problems view.
	 */
	public void showDefaultProblemColumns() {
		openConfigureColumnsShell();
		
		new PushButton("Restore Defaults").click();
		
		confirmChangesAndCloseConfigureColumnsShell();
	}
	
	private void openConfigureColumnsShell() {
		activate();
		new ViewMenu("View Menu", "Configure Columns...").select();
		
		new DefaultShell("Configure Columns");
	}
	
	private void confirmChangesAndCloseConfigureColumnsShell() {
		new OkButton().click();
		
		new WaitWhile(new ShellWithTextIsAvailable("Configure Columns"));
	}
	
	/**
	 * Gets index of a specific column in problems view.
	 * 
	 * @param column column to get index of
	 * @return index of a specified column
	 */
	public int getIndexOfColumn(String column) {
		return getColumnIndex(new DefaultTree().getHeaderColumns(), column);
	}
	
	/**
	 * Gets index of a specific column in problems view.
	 * 
	 * @param column column to get index of
	 * @return index of a specified column
	 */
	public int getIndexOfColumn(Column column) {
		return getColumnIndex(new DefaultTree().getHeaderColumns(), column.toString());
	}	
	
	private int getColumnIndex(List<String> columns, String column) {
		int index = 0;
		while (index < columns.size()) {
			if (columns.get(index).equals(column)) {
				return index;
			}
			index++;
		}
		throw new EclipseLayerException("Specified column " + column + " is not presented in a tree of problems view.");
	}
	
	/**
	 * Enum for columns of a tree in problems view.
	 * 
	 * @author mlabuda@redhat.com
	 * @since 0.7
	 */
	public enum Column {
		
		DESCRIPTION("Description"),
		RESOURCE("Resource"),
		PATH("Path"),
		ID("ID"),
		LOCATION("Location"),
		TYPE("Type"),
		CREATION_TIME("Creation Time");
		
		private final String text;
		
		private Column(String text) {
			this.text = text;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			return text;
		}
	}
	
	/**
	 * Enum for type of a problem. Currently only warning and errors are supported although there is also info type. 
	 * 
	 * @author mlabuda@redhat.com
	 * @since 0.7
	 */
	public enum ProblemType {
		WARNING, ERROR, ANY
	}
	
	/**
	 * Returns true if Problems view marker is updating its UI
	 * 
	 */
	@SuppressWarnings("restriction")
	private class ProblemsViewMarkerIsUpdating extends AbstractExtendedMarkersViewIsUpdating {

		/**
		 * Construct the condition.
		 */
		public ProblemsViewMarkerIsUpdating() {
			super(ProblemsView.this, org.eclipse.ui.internal.views.markers.ProblemsView.class);
		}
	}
}
