package org.jboss.reddeer.swt.impl.tree;

import org.eclipse.swt.widgets.TreeItem;
/**
 * Basic Tree implementation enclosing SWT Tree object
 * @author vpakan
 *
 */
public class BasicTreeItem extends AbstractTreeItem {

  public BasicTreeItem (TreeItem swtTreeItem){
    super(swtTreeItem);
  }

}
