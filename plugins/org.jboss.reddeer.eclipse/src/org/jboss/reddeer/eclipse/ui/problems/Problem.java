package org.jboss.reddeer.eclipse.ui.problems;

import java.util.List;

import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.Column;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.ProblemType;
import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Problem represents an error or warning in problems view. Get methods return values of a specific column
 * of a problem. If there is no value for a specific column or a column is hidden, null is returned.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class Problem {

	private ProblemType problemType;
	private String description;
	private String resource;
	private String path;
	private String id;
	private String location;
	private String type;
	private String creationTime;
		
	/**
	 * Creates a new problem of Problems view. 
	 * 
	 * @param problemType type of a problem [warning|error]
	 * @param item tree item of a problem
	 */
	public Problem(ProblemType problemType, TreeItem item) {
		this.problemType = problemType;
		
		ProblemsView problemsView = new ProblemsView();
		List<String> columns = problemsView.getProblemColumns();
		if (columns.contains(Column.DESCRIPTION.toString())) {
			description = item.getCell(problemsView.getIndexOfColumn(Column.DESCRIPTION));
		}
		if (columns.contains(Column.RESOURCE.toString())) {
			resource = item.getCell(problemsView.getIndexOfColumn(Column.RESOURCE));
		}
		if (columns.contains(Column.PATH.toString())) {
			path = item.getCell(problemsView.getIndexOfColumn(Column.PATH));
		}
		if (columns.contains(Column.ID.toString())) {
			id = item.getCell(problemsView.getIndexOfColumn(Column.ID));
		}
		if (columns.contains(Column.LOCATION.toString())) {
			location = item.getCell(problemsView.getIndexOfColumn(Column.LOCATION));
		}
		if (columns.contains(Column.TYPE.toString())) {
			type = item.getCell(problemsView.getIndexOfColumn(Column.TYPE));
		}
		if (columns.contains(Column.CREATION_TIME.toString())) {
			creationTime = item.getCell(problemsView.getIndexOfColumn(Column.CREATION_TIME));
		}
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
		return description;
	}

	/**
	 * Gets resource of a problem - place where the problem is occurred.
	 * 
	 * @return resource of the problem
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * Gets path to the problem. If getResource returns only package, this is empty. If getResource contains a class
	 * of a problem then path contains specific package containing the class.
	 * 
	 * @return path to the problem
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Gets ID of the problem. ID is numeric value.
	 * 
	 * @return ID of the problem
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets location of the problem. Location is either line in a file containing the problem or contains information
	 * that problem is present on build path etc.
	 * 
	 * @return location of the problem
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Gets nature of the problem.
	 * 
	 * @return nature of the problem
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets time when the problem was created.
	 * 
	 * @return creation time of the problem
	 */
	public String getCreationTime() {
		return creationTime;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
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
