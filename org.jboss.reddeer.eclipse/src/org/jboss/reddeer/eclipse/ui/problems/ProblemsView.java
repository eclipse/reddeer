package org.jboss.reddeer.eclipse.ui.problems;

import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.ViewTree;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;

public class ProblemsView extends WorkbenchView{

	public ProblemsView() {
		super("Problems");
	}

	/**
	 * Return list of error problem markers
	 * 
	 * @return
	 */
	public List<TreeItem> getAllErrors(){
		if (!viewObject.isActive()){
			viewObject.setFocus();
		}
		
		ViewTree tree = new ViewTree();
		return filter(tree.getItems(), true);
	}
	
	/**
	 * Return list of warnings problem markers
	 * 
	 * @return
	 */
	public List<TreeItem> getAllWarnings(){
		if (!viewObject.isActive()){
			viewObject.setFocus();
		}
		ViewTree tree = new ViewTree();
		return filter(tree.getItems(), false);
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
	
}
