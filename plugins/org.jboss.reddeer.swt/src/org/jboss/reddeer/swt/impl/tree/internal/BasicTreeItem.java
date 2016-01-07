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
