package org.jboss.reddeer.eclipse.ui.problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.eclipse.condition.AbstractExtendedMarkersViewIsUpdating;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.ui.problems.matcher.AbstractProblemMatcher;
import org.jboss.reddeer.swt.api.TreeItem;
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
			if (problemSeverityTreeItem.getText().matches("^Errors \\(\\d+ item.*\\)") 
					&& problemType == ProblemType.ERROR) {
				return problemSeverityTreeItem.getItems();
			} 
			if (problemSeverityTreeItem.getText().matches("^Warnings \\(\\d+ item.*\\)")
					&& problemType == ProblemType.WARNING) {
				return problemSeverityTreeItem.getItems();
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
			for (AbstractProblemMatcher matcher: matchers) {
				if (!matcher.matches(item.getCell(getIndexOfColumn(matcher.getColumn())))) {
					itemFitsMatchers = false;
					break;
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
