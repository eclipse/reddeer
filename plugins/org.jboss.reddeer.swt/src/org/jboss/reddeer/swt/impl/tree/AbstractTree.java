package org.jboss.reddeer.swt.impl.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.junit.logging.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.tree.internal.BasicTreeItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

public abstract class AbstractTree implements Tree {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	protected org.eclipse.swt.widgets.Tree swtTree;
	
	protected AbstractTree (org.eclipse.swt.widgets.Tree swtTree){
	  if (swtTree != null){
	    this.swtTree = swtTree;  
	  }
	  else {
	     throw new SWTLayerException("SWT Tree passed to constructor is null");
	  }	  
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

	public List<TreeItem> getItems() {
	  return Display.syncExec(new ResultRunnable<List<TreeItem>>() {
      @Override
      public List<TreeItem> run() {
        LinkedList<TreeItem> result = new LinkedList<TreeItem>();
        for (org.eclipse.swt.widgets.TreeItem swtTreeItem : swtTree.getItems()){
          result.addLast(new BasicTreeItem(swtTreeItem));
        }
        return result;
      }
    });
  }
	
  public void selectItems(final TreeItem... treeItems){
	logger.debug("Selecting tree items: ");
    setFocus();
    Display.syncExec(new Runnable() {
      public void run() {
        List<org.eclipse.swt.widgets.TreeItem> selection = new ArrayList<org.eclipse.swt.widgets.TreeItem>();
        for (TreeItem treeItem : treeItems) {
          logger.debug("Tree item to add to selection: " + treeItem.getSWTWidget().getText());
          selection.add(treeItem.getSWTWidget());
        }
        if (!(SWT.MULTI == (swtTree.getStyle() & SWT.MULTI)) && treeItems.length > 1)
          logger.warn("Tree does not support SWT.MULTI, cannot make multiple selections"); //$NON-NLS-1$
        logger.debug("Setting Tree selection");
        swtTree.setSelection(selection.toArray(new org.eclipse.swt.widgets.TreeItem[] {}));
      }
    });
    notifySelect();
    logger.info("Selected Tree Items:");
    for (TreeItem treeItem : treeItems){
    	logger.info("  " + treeItem);
    }
  }
  
  public void setFocus(){
    Display.syncExec(new Runnable() {
      public void run() {
        if (!swtTree.isFocusControl()){
          logger.debug("Setting focus to Tree");	
          swtTree.forceFocus();
        }
      }
    });
  }


  /**
   * @see Tree#getColumnCount()
   */
  public int getColumnCount () {
	  return Display.syncExec(new ResultRunnable<Integer>() {
		 @Override
		 public Integer run() {
			 return swtTree.getColumnCount();
		 }
	  });
	}

  public void unselectAllItems(){
    Display.syncExec(new Runnable() {
      public void run() {
    	logger.debug("Unselect all tree items");
        swtTree.deselectAll();
      }
    });    
    notifySelect();
    logger.info("All tree items unselected");
  }
  
	public org.eclipse.swt.widgets.Tree getSWTWidget(){
	  return swtTree;
	}
  /**
   * Notifies listeners about selection event
   * 
   */
  private void notifySelect() {
    Display.syncExec(new Runnable() {
      public void run() {
    	logger.debug("Notify Tree about selection event");
        swtTree.notifyListeners(SWT.Selection, createSelectionEvent());
      }
    });
  }
  /**
   * Creates selection event 
   * @return
   */
  private Event createSelectionEvent() {
    Event event = new Event();
    event.display = Display.getDisplay();
    event.time = (int) System.currentTimeMillis();
    event.widget = swtTree;
    event.type = SWT.Selection;
    return event;
  }
}
