package org.jboss.reddeer.swt.internal;

import org.eclipse.swt.widgets.ExpandItem;
import org.jboss.reddeer.swt.impl.expandbar.AbstractExpandBarItem;
/**
 * Basic Expand Bar Item implementation enclosing SWT Expand Item object
 * @author Vlado Pakan
 *
 */
public class BasicExpandBarItem extends AbstractExpandBarItem {

  public BasicExpandBarItem (ExpandItem swtExpandBarItem){
    super(swtExpandBarItem);
  }

}
