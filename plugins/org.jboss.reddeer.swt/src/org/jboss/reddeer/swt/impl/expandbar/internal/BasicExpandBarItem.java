package org.jboss.reddeer.swt.impl.expandbar.internal;

import org.eclipse.swt.widgets.ExpandItem;
import org.jboss.reddeer.swt.impl.expandbar.AbstractExpandBarItem;
/**
 * Basic Expand Bar Item implementation enclosing SWT Expand Item object
 * @author Vlado Pakan
 *
 */
public class BasicExpandBarItem extends AbstractExpandBarItem {

  /**
   * Instantiates a new basic expand bar item.
   *
   * @param swtExpandBarItem the swt expand bar item
   */
  public BasicExpandBarItem (ExpandItem swtExpandBarItem){
    super(swtExpandBarItem);
  }

}
