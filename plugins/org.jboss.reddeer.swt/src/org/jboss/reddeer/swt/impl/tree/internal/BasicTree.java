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
