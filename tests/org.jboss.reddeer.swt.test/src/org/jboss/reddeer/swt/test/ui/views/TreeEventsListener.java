package org.jboss.reddeer.swt.test.ui.views;

import org.jboss.reddeer.junit.logging.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.AbstractTreeItem;
import org.jboss.reddeer.swt.util.Display;
/**
 * Defines Tree listeners for tests of Tree and TreeItem
 * @author vpakan
 *
 */
public class TreeEventsListener implements SelectionListener, TreeListener, FocusListener, Listener {
  private final Logger logger = Logger.getLogger(this.getClass());
  
  private boolean focusGained = false;
  private boolean focusLost = false;
  private boolean checkEvent = false;
  private boolean selectionEvent = false;
  private boolean mouseDoubleClickEvent = false;
  private TreeItem collapsedTreeItem = null;
  private TreeItem expandedTreeItem = null;
  private TreeItem selectedTreeItem = null;
  private TreeItem defaultSelectedTreeItem = null;
  
  private Tree tree;
  
  public TreeEventsListener (Tree tree){
    this.tree = tree;
  }
  /**
   * Adds all necessary listeners 
   */
  public void addListeners(){
    final TreeEventsListener tel = this;
    Display.syncExec(new Runnable() {
      @Override
      public void run() {
        tree.addSelectionListener(tel);
        tree.addFocusListener(tel);
        tree.addTreeListener(tel);
        tree.addListener(SWT.MouseDoubleClick, tel);
      }
    });
  }
  /**
   * Removes all necessary listeners 
   */
  public void removeListeners(){
    final TreeEventsListener tel = this;
    Display.syncExec(new Runnable() {
      @Override
      public void run() {
        tree.removeSelectionListener(tel);
        tree.removeFocusListener(tel);
        tree.removeTreeListener(tel);
      }
    });
  }
  @Override
  public void focusGained(FocusEvent arg0) {
    logger.debug("Calling method focusGained()");
    focusGained = true;
  }
  @Override
  public void focusLost(FocusEvent arg0) {
    logger.debug("Calling method focusLost()");
    focusLost = true;
  }

  @Override
  public void treeCollapsed(TreeEvent arg0) {
    logger.debug("Calling method treeCollapsed()");
    collapsedTreeItem = new TestTreeItem((org.eclipse.swt.widgets.TreeItem)arg0.item);
  }

  @Override
  public void treeExpanded(TreeEvent arg0) {
    logger.debug("Calling method treeExpanded()");
    expandedTreeItem = new TestTreeItem((org.eclipse.swt.widgets.TreeItem)arg0.item);    
  }

  @Override
  public void widgetDefaultSelected(SelectionEvent arg0) {
    logger.debug("Calling method widgetDefaultSelected()");
    if (arg0.item != null){
      defaultSelectedTreeItem = new TestTreeItem((org.eclipse.swt.widgets.TreeItem)arg0.item);    
    }
    else{
      defaultSelectedTreeItem = null;
    }
  }

  @Override
  public void widgetSelected(SelectionEvent arg0) {
    logger.debug("Calling method widgetSelected()");
    checkEvent = arg0.detail == SWT.CHECK;
    logger.debug("It was check event: " + checkEvent);
    if (arg0.item != null){
      selectedTreeItem = new TestTreeItem((org.eclipse.swt.widgets.TreeItem)arg0.item);
    }
    else{
      selectedTreeItem = null;
    }
    selectionEvent = true;
  }
  /**
   * Resets variables watching listeners invocations
   */
  public void resetListeningWatchers(){
    focusGained = false;
    focusLost = false;
    checkEvent = false;
    selectionEvent = false;
    mouseDoubleClickEvent = false;
    collapsedTreeItem = null;
    expandedTreeItem = null;
    selectedTreeItem = null;
    defaultSelectedTreeItem = null;
  }
  public boolean wasFocusGained() {
    return focusGained;
  }
  public boolean wasFocusLost() {
    return focusLost;
  }
  public boolean wasCheckEvent() {
    return checkEvent;
  }
  public boolean wasSelectionEvent() {
    return selectionEvent;
  }
  public boolean wasMouseDoubleClickEvent() {
    return mouseDoubleClickEvent;
  }
  public TreeItem getCollapsedTreeItem() {
    return collapsedTreeItem;
  }
  public TreeItem getExpandedTreeItem() {
    return expandedTreeItem;
  }
  public TreeItem getSelectedTreeItem() {
    return selectedTreeItem;
  }
  public TreeItem getDefaultSelectedTreeItem() {
    return defaultSelectedTreeItem;
  }
  @Override
  public void handleEvent(Event arg0) {
    logger.debug("Calling method handleEvent() with event type: " + arg0.type);
    switch (arg0.type) {
    case SWT.MouseDoubleClick:
      logger.debug("SWT.MouseDoubleClick event was fired");
      mouseDoubleClickEvent = true;
      break;
    default:
      break;
    }
  }
  private class TestTreeItem extends AbstractTreeItem {

	  public TestTreeItem (org.eclipse.swt.widgets.TreeItem swtTreeItem){
	    super(swtTreeItem);
	  }

  }
}
