package org.jboss.reddeer.swt.impl.tree.internal;

import org.eclipse.swt.widgets.TreeItem;
import org.jboss.reddeer.swt.impl.tree.AbstractTreeItem;
/**
 * Basic Tree implementation enclosing SWT Tree object
 * @author vpakan
 *
 */
public class BasicTreeItem extends AbstractTreeItem {

  /**
   * Instantiates a new basic tree item.
   *
   * @param swtTreeItem the swt tree item
   */
  public BasicTreeItem (TreeItem swtTreeItem){
    super(swtTreeItem);
  }

}
