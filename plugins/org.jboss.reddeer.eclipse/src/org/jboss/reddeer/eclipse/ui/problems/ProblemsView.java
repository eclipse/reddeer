package org.jboss.reddeer.eclipse.ui.problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.condition.MarkerIsUpdating;
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
	 * @return
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
	 * @return
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
	 */
	public List<TreeItem> getErrors(Matcher<String> description,
			Matcher<String> resource, Matcher<String> path,
			Matcher<String> location, Matcher<String> type) {
		activate();
		return filter(getAllErrors(), description, resource, path,
				location, type);
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
	 */
	public List<TreeItem> getWarnings(Matcher<String> description,
			Matcher<String> resource, Matcher<String> path,
			Matcher<String> location, Matcher<String> type) {
		activate();
		return filter(getAllWarnings(), description, resource, path,
				location, type);
	}

	private List<TreeItem> filter(List<TreeItem> list, boolean errors){
		for (TreeItem abstractTreeItem : list) {
			if (abstractTreeItem.getText().matches("^Errors \\(\\d+ item.*\\)") && errors) {
				return abstractTreeItem.getItems();
			} 
			if (abstractTreeItem.getText().matches("^Warnings \\(\\d+ item.*\\)") && !errors) {
				return abstractTreeItem.getItems();
			}
		}
		return new LinkedList<TreeItem>();
	}

	private List<TreeItem> filter(List<TreeItem> items,
			Matcher<String> description,
			Matcher<String> resource, Matcher<String> path,
			Matcher<String> location, Matcher<String> type) {
		List<TreeItem> filteredResult = new ArrayList<TreeItem>();
		for (TreeItem item : items) {
			boolean descriptionIsOk = description == null || description.matches(item.getCell(0));
			boolean resourceIsOk = resource == null || resource.matches(item.getCell(1));
			boolean pathIsOk = path == null || path.matches(item.getCell(2));
			boolean locationIsOk = location == null || location.matches(item.getCell(3));
			boolean typeIsOk = type == null || type.matches(item.getCell(4));

			if (descriptionIsOk && resourceIsOk && pathIsOk && locationIsOk && typeIsOk) {
				filteredResult.add(item);
			}
		}
		return filteredResult;
	}
}
