package org.jboss.reddeer.eclipse.datatools.sqltools.result.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.platform.OS;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * SQL Result View RedDeer implementation
 * 
 * @author Jiri Peterka
 *
 */
public class ResultView extends WorkbenchView {

	/**
	 * Create instance and looks for Result view.
	 */
	public ResultView() {
		super("SQL Results");
	}

	/**
	 * Return SQL results.
	 * 
	 * @return list of SQLResults
	 */
	public List<SQLResult> getResults() {
		open();
		DefaultTree tree = new DefaultTree();
		List<TreeItem> items = tree.getAllItems();
		List<SQLResult> results = new ArrayList<SQLResult>();

		for (TreeItem item : items) {

			results.add(new SQLResult(item.getCell(0), item.getCell(1), item
					.getCell(2), item.getCell(3)));

		}
		return results;
	}

	/**
	 * Removes all visible results from the SQL Result View.
	 */
	public void removeAllResults() {
		open();
		String tooltip = "Remove All Visible Results (Shift+Delete)";
		if (RunningPlatform.isOSX()) {
			tooltip = "Remove All Visible Results (⇧⌦)";
		}
		DefaultToolItem item = new DefaultToolItem(tooltip);
		item.click();
		AbstractWait.sleep(TimePeriod.SHORT);
	}

}
