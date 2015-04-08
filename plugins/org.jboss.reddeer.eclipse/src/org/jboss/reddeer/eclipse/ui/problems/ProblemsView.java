package org.jboss.reddeer.eclipse.ui.problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.condition.MarkerIsUpdating;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.ui.problems.matcher.AbstractProblemMatcher;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
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
	 * Return list of error problem markers
	 * 
	 * @return list of tree items of errors
	 * @deprecated since 0.7. Use getProblems with ProblemType.ERROR and no column matcher to get all errors.
	 */
	@Deprecated
	public List<TreeItem> getAllErrors(){
		activate();
		new WaitUntil(new MarkerIsUpdating(),TimePeriod.SHORT,false);
		new WaitWhile(new MarkerIsUpdating());
		DefaultTree tree = new DefaultTree();
		return filter(tree.getItems(), true);
	}
	
	/**
	 * Return list of warnings problem markers
	 * 
	 * @return list of tree items of errors
	 * @deprecated since 0.7. Use getProblems with ProblemType.WARNING and no column matcher to get all warnings. 
	 */
	@Deprecated
	public List<TreeItem> getAllWarnings(){
		activate();
		new WaitUntil(new MarkerIsUpdating(),TimePeriod.SHORT,false);
		new WaitWhile(new MarkerIsUpdating());
		DefaultTree tree = new DefaultTree();
		return filter(tree.getItems(), false);
	}
	
	/**
	 * Return list of error problem markers that match given {@link Matcher}s.
	 * 
	 * @param description filter by description
	 * @param resource filter by resource
	 * @param path filter by path
	 * @param location filter by location
	 * @param type filter by type
	 * @return filtered list of errors that match given {@link Matcher}s
	 * @deprecated since 0.7. Use getProblems with ProblemType.ERROR and desired matchers.
	 */
	@Deprecated
	public List<TreeItem> getErrors(Matcher<String> description,
			Matcher<String> resource, Matcher<String> path,
			Matcher<String> location, Matcher<String> type) {
		activate();
		return filter(getAllErrors(), description, resource, path,
				location, type, null, null);
	}
	
	/**
	 * Return list of warning problem markers that match given {@link Matcher}s.
	 * 
	 * @param description filter by description
	 * @param resource filter by resource
	 * @param path filter by path
	 * @param location filter by location
	 * @param type filter by type
	 * @return filtered list of warnings that match given {@link Matcher}s
	 * @deprecated since 0.7. Use getProblems with ProblemType.WARNING and desired matchers.
	 */
	@Deprecated
	public List<TreeItem> getWarnings(Matcher<String> description,
			Matcher<String> resource, Matcher<String> path,
			Matcher<String> location, Matcher<String> type) {
		activate();
		return filter(getAllWarnings(), description, resource, path,
				location, type, null, null);
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
		new WaitUntil(new MarkerIsUpdating(),TimePeriod.SHORT,false);
		new WaitWhile(new MarkerIsUpdating());
		DefaultTree tree = new DefaultTree();
		return filterProblems(problemType, tree.getItems(), matchers);
	}
	
	private List<TreeItem> filter(List<TreeItem> list, boolean errors){
		for (TreeItem problemSeverityTreeItem : list) {
			if (problemSeverityTreeItem.getText().matches("^Errors \\(\\d+ item.*\\)") && errors) {
				return problemSeverityTreeItem.getItems();
			} 
			if (problemSeverityTreeItem.getText().matches("^Warnings \\(\\d+ item.*\\)") && !errors) {
				return problemSeverityTreeItem.getItems();
			}
		}
		return new LinkedList<TreeItem>();
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
	
	private List<TreeItem> filter(List<TreeItem> items, Matcher<String> description, 
			Matcher<String> resource, Matcher<String> path, Matcher<String> location, 
			Matcher<String> type, Matcher<String> id, Matcher<String> creationTime) {
		List<TreeItem> filteredResult = new ArrayList<TreeItem>();
		for (TreeItem item : items) {
			boolean descriptionIsOk = description == null ||
						description.matches(item.getCell(getIndexOfColumn(Column.DESCRIPTION)));
			boolean resourceIsOk = resource == null || 
						resource.matches(item.getCell(getIndexOfColumn(Column.RESOURCE)));
			boolean pathIsOk = path == null || 
						path.matches(item.getCell(getIndexOfColumn(Column.PATH)));
			boolean locationIsOk = location == null || 
						location.matches(item.getCell(getIndexOfColumn(Column.LOCATION)));
			boolean typeIsOk = type == null || 
						type.matches(item.getCell(getIndexOfColumn(Column.TYPE)));
			boolean idIsOk = id == null ||
						id.matches(item.getCell(getIndexOfColumn(Column.ID)));
			boolean creationTimeIsOk = creationTime == null ||
						creationTime.matches(item.getCell(getIndexOfColumn(Column.CREATION_TIME)));
			
			if (descriptionIsOk && resourceIsOk && pathIsOk && locationIsOk && typeIsOk 
					&& idIsOk && creationTimeIsOk) {
				filteredResult.add(item);
			}
		}
		return filteredResult;
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
}
