package org.jboss.reddeer.eclipse.ui.problems;

import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.view.ViewMatcher;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;

public class ProblemsView extends WorkbenchView{

	public ProblemsView() {
		super("Problems");
	}

	/**
	 * Returns array of all errors
	 * 
	 * @return
	 */
	
	public List<TreeItem> getAllErrors(){
		if (!viewObject.isActive()){
			viewObject.setFocus();
		}
		DefaultTree tree = new DefaultTree(new ViewMatcher(this));
		
		return filter(tree.getAllItems(), true);
	}
	
	public List<TreeItem> getAllWarnings(){
		if (!viewObject.isActive()){
			viewObject.setFocus();
		}
		DefaultTree tree = new DefaultTree(new ViewMatcher(this));
		
		return filter(tree.getAllItems(), false);
	}
	
	/**
	 * 		
	 * @param list
	 * @param errorsWarnings true to get errors, false to get warnings
	 * @return
	 */
	
	private List<TreeItem> filter(List<TreeItem> list, boolean errorsWarnings){
		List<TreeItem> outputList = new LinkedList<TreeItem>();
		boolean errors=false;
		for (TreeItem abstractTreeItem : list) {
			if (errors == errorsWarnings){
				if (!(abstractTreeItem.getText().matches("^Errors \\(\\d+ item.*\\)")) && 
					!(abstractTreeItem.getText().matches("^Warnings \\(\\d+ item.*\\)"))){
						outputList.add(abstractTreeItem);
				}
			}
			if (abstractTreeItem.getText().matches("^Errors \\(\\d+ item.*\\)")){
				errors = true;
			}else if (abstractTreeItem.getText().matches("^Warnings \\(\\d+ item.*\\)")) {
				errors = false;
			}
		}
		
		return outputList;
	}
	
}
