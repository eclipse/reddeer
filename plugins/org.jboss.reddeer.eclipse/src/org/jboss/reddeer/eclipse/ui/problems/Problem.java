package org.jboss.reddeer.eclipse.ui.problems;

import java.util.List;

import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.Column;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.ProblemType;
import org.jboss.reddeer.eclipse.ui.views.markers.QuickFixWizard;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;

/**
 * Problem represents an error or warning in problems view. Get methods return values of a specific column
 * of a problem. If there is no value for a specific column or a column is hidden, null is returned.
 * 
 * @author mlabuda@redhat.com
 * @author rawagner
 * @since 0.7
 */
public class Problem {
	
	private TreeItem problemItem;

	private ProblemType problemType;
		
	/**
	 * Creates a new problem of Problems view. 
	 * 
	 * @param problemType type of a problem [warning|error]
	 * @param item tree item of a problem
	 */
	public Problem(ProblemType problemType, TreeItem item) {
		this.problemType = problemType;
		this.problemItem = item;
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

	/**
	 * Gets description of the problem. Description contains specific information about problem.
	 * 
	 * @return description of the problem
	 */
	public String getDescription() {
		return getCell(Column.DESCRIPTION);
	}

	/**
	 * Gets resource of a problem - place where the problem is occurred.
	 * 
	 * @return resource of the problem
	 */
	public String getResource() {
		return getCell(Column.RESOURCE);
	}

	/**
	 * Gets path to the problem. If getResource returns only package, this is empty. If getResource contains a class
	 * of a problem then path contains specific package containing the class.
	 * 
	 * @return path to the problem
	 */
	public String getPath() {
		return getCell(Column.PATH);
	}

	/**
	 * Gets ID of the problem. ID is numeric value.
	 * 
	 * @return ID of the problem
	 */
	public String getId() {
		return getCell(Column.ID);
	}

	/**
	 * Gets location of the problem. Location is either line in a file containing the problem or contains information
	 * that problem is present on build path etc.
	 * 
	 * @return location of the problem
	 */
	public String getLocation() {
		return getCell(Column.LOCATION);
	}

	/**
	 * Gets nature of the problem.
	 * 
	 * @return nature of the problem
	 */
	public String getType() {
		return getCell(Column.TYPE);
	}

	/**
	 * Gets time when the problem was created.
	 * 
	 * @return creation time of the problem
	 */
	public String getCreationTime() {
		return getCell(Column.CREATION_TIME);
	}
	
	/**
	 * Open quickfix
	 * @return Quickfix wizard
	 */
	public QuickFixWizard openQuickFix(){
		problemItem.select();
		new ContextMenu("Quick Fix").select();
		return new QuickFixWizard();
	}
	
	private String getCell(Column column){
		ProblemsView problemsView = new ProblemsView();
		List<String> columns = problemsView.getProblemColumns();
		if (columns.contains(column.toString())) {
			return problemItem.getCell(problemsView.getIndexOfColumn(column));
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		String description = getDescription();
		String resource = getResource(); 
		String path = getPath();
		String id = getId();
		String location = getLocation();
		String type = getType();
		String creationTime = getCreationTime();
		
		if (description != null) {
			builder.append("description: '" + description + "'; ");
		}
		if (resource != null) {
			builder.append("resource: '" + resource + "'; ");
		}
		if (path != null) {
			builder.append("path: '" + path + "'; ");
		}
		if (id != null) {
			builder.append("id: '" + id + "'; ");
		}
		if (location != null) {
			builder.append("location: '" + location + "'; ");
		}
		if (type != null) {
			builder.append("type: '" + type + "'; ");
		}
		if (creationTime != null) {
			builder.append("creationTime: '" + creationTime + "'; ");
		}
		return builder.toString();
	}
}
