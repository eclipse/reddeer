package org.jboss.reddeer.swt.impl.tree;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;
import org.jboss.reddeer.swt.util.Display;

/**
 * Default Tree implementation
 * 
 * @author jjankovi
 *
 */
public class DefaultTree implements Tree{

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	private SWTBotTree tree;
	private int index;
	
	/**
	 * Default parameter-less constructor
	 */
	public DefaultTree() {
		this(0);
	}

	/**
	 * Tree with specified index will be constructed 
	 * 
	 * @param index
	 */
	@SuppressWarnings("unchecked")
	public DefaultTree(int index) {
		tree = new SWTBotTree((org.eclipse.swt.widgets.Tree) 
				WidgetLookup.getInstance().
				activeWidget(allOf(widgetOfType(
						org.eclipse.swt.widgets.Tree.class)), index));
		this.index = index;
	}
	
	public List<TreeItem> getItems(){
		List<TreeItem> list = new LinkedList<TreeItem>();
		for (SWTBotTreeItem treeItem : tree.getAllItems()) {
			TreeItem item = null;
			item = new DefaultTreeItem(index,treeItem.getText());
			list.add(item);
		}
		return list;	
	}
	
	public List<TreeItem> getAllItems(){
		List<TreeItem> list = new LinkedList<TreeItem>();
		list.addAll(getAllItemsRecursive(getItems()));
		return list;
	}

	private List<TreeItem> getAllItemsRecursive(List<TreeItem> parentItems){
		List<TreeItem> list = new LinkedList<TreeItem>();
		
		for (TreeItem item : parentItems) {
			list.add(item);
			list.addAll(getAllItemsRecursive(item.getItems()));
		}
		return list;
	}
	
  /**
   * Selects tree items
   * @param selectItems - array of tree items to select
   */
  public void selectItems (final TreeItem[] selectItems){
    /*
    Display.syncExec(new Runnable() {
      @Override
      public void run() {
        ArrayList<org.eclipse.swt.widgets.TreeItem> selection = new ArrayList<org.eclipse.swt.widgets.TreeItem>();

        for (TreeItem item : selectItems) {
          selection.add(item.getSWTWidget());
        }
        tree.widget.setFocus();
        tree.widget.setSelection(selection
            .toArray(new org.eclipse.swt.widgets.TreeItem[] {}));
      }
    });
    */
    // notifySelect();
    ArrayList<SWTBotTreeItem> selection = new ArrayList<SWTBotTreeItem>();

    for (TreeItem item : selectItems) {
      selection.add(new SWTBotTreeItem(item.getSWTWidget()));
    }
    tree.select(selection.toArray(new SWTBotTreeItem[] {}));    
    
  }

}
