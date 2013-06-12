package org.jboss.reddeer.swt.api;

import java.util.List;

/**
 * API for Tree manipulation
 * @author Jiri Peterka
 *
 */
public interface Tree {

	/**
	 * @return Top level tree items. 
	 *  
	 */
	List<TreeItem> getItems();
			
	/** 
	 * @return All tree items recursively.
	 */
	List<TreeItem> getAllItems();
	
	/** 
   * @return Selects items.
   */
  void selectItems(TreeItem... treeItems);
  /**
   * Sets focus to tree
   */
  void setFocus();
  /**
   * Unselects all selected items
   */
  public void unselectAllItems();
  
	org.eclipse.swt.widgets.Tree getSWTWidget();
}
