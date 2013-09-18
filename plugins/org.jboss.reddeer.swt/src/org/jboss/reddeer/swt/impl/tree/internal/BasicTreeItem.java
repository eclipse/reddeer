package org.jboss.reddeer.swt.impl.tree.internal;

import org.eclipse.swt.widgets.TreeItem;
import org.jboss.reddeer.swt.impl.tree.AbstractTreeItem;
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
