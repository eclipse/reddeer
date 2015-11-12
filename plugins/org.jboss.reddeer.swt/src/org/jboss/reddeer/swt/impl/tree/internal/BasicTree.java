package org.jboss.reddeer.swt.impl.tree.internal;

import org.eclipse.swt.widgets.Tree;
import org.jboss.reddeer.swt.impl.tree.AbstractTree;
/**
 * Basic Tree implementation enclosing SWT Tree object
 * @author vpakan
 *
 */
public class BasicTree extends AbstractTree{

  /**
   * Instantiates a new basic tree.
   *
   * @param swtTree the swt tree
   */
  public BasicTree (Tree swtTree){
    super(swtTree);
  }
}
