package org.jboss.reddeer.swt.impl.tree;

import org.eclipse.swt.widgets.Tree;
/**
 * Basic Tree implementation enclosing SWT Tree object
 * @author vpakan
 *
 */
public class BasicTree extends AbstractTree{

  public BasicTree (Tree swtTree){
    super(swtTree);
  }
}
