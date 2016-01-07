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
package org.jboss.reddeer.swt.impl.table.internal;

import org.eclipse.swt.widgets.TableItem;
import org.jboss.reddeer.swt.impl.table.AbstractTableItem;

public class BasicTableItem extends AbstractTableItem{

	/**
	 * Instantiates a new basic table item.
	 *
	 * @param swtTableItem the swt table item
	 */
	public BasicTableItem(TableItem swtTableItem){
		super(swtTableItem);
	}
}
