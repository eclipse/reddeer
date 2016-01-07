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
