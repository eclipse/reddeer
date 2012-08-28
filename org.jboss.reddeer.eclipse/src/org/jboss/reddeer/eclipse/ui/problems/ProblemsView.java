package org.jboss.reddeer.eclipse.ui.problems;

import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.swt.impl.tree.AbstractTreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
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
	
	public List<AbstractTreeItem> getAllErrors(){
		if (!viewObject.isActive()){
			viewObject.setFocus();
		}
		DefaultTree tree = new DefaultTree(viewObject.bot());
		
		return filter(tree.getAllItemsRecursive(), true);
	}
	
	public List<AbstractTreeItem> getAllWarnings(){
		if (!viewObject.isActive()){
			viewObject.setFocus();
		}
		DefaultTree tree = new DefaultTree(viewObject.bot());
		
		return filter(tree.getAllItemsRecursive(), false);
	}
	
	/**
	 * 		
	 * @param list
	 * @param errorsWarnings true to get errors, false to get warnings
	 * @return
	 */
	
	private List<AbstractTreeItem> filter(List<AbstractTreeItem> list, boolean errorsWarnings){
		List<AbstractTreeItem> outputList = new LinkedList<AbstractTreeItem>();
		boolean errors=false;
		for (AbstractTreeItem abstractTreeItem : list) {
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
