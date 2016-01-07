/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
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
