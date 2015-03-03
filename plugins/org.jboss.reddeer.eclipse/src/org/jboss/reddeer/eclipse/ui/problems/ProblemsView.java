package org.jboss.reddeer.eclipse.ui.problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.condition.MarkerIsUpdating;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
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
	 */
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
	 */
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
	 * @deprecated since 0.7
	 */
	@Deprecated
	public List<TreeItem> getErrors(Matcher<String> description,
			Matcher<String> resource, Matcher<String> path,
			Matcher<String> location, Matcher<String> type) {
		return getErrors(description, resource, path,
				location, type, null, null);
	}

	/**
	 * Return list of error problem markers that match given {@link Matcher}s.
	 * 
	 * @param description filter by description
	 * @param resource filter by resource
	 * @param path filter by path
	 * @param location filter by location
	 * @param type filter by type
	 * @param id filter by id
	 * @param creationTime filter by creation time
	 * @return filtered list of errors that match given {@link Matcher}s
	 */
	public List<TreeItem> getErrors(Matcher<String> description,
			Matcher<String> resource, Matcher<String> path, Matcher<String> location, 
			Matcher<String> type, Matcher<String> id, Matcher<String> creationTime) {
		activate();
		return filter(getAllErrors(), description, resource, path,
				location, type, id, creationTime);
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
	 * @deprecated since 0.7.
	 */
	@Deprecated
	public List<TreeItem> getWarnings(Matcher<String> description,
			Matcher<String> resource, Matcher<String> path,
			Matcher<String> location, Matcher<String> type) {
		return getWarnings(description, resource, path,
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
	 * @param id filter by id
	 * @param id filter by creation time
	 * @return filtered list of warnings that match given {@link Matcher}s
	 */
	public List<TreeItem> getWarnings(Matcher<String> description,
			Matcher<String> resource, Matcher<String> path,	Matcher<String> location, 
			Matcher<String> type, Matcher<String> id, Matcher<String> creationTime) {
		activate();
		return filter(getAllWarnings(), description, resource, path,
				location, type, id, creationTime);
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
}
