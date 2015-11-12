package org.jboss.reddeer.swt.impl.expandbar.internal;

import org.eclipse.swt.widgets.ExpandBar;
import org.jboss.reddeer.swt.impl.expandbar.AbstractExpandBar;
/**
 * Basic Expand Bar implementation enclosing SWT Expand Bar object
 * @author Vlado Pakan
 *
 */
public class BasicExpandBar extends AbstractExpandBar{

  /**
   * Instantiates a new basic expand bar.
   *
   * @param swtExpandBar the swt expand bar
   */
  public BasicExpandBar (ExpandBar swtExpandBar){
    super(swtExpandBar);
  }
}
