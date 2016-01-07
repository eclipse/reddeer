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
package org.jboss.reddeer.gef.comparator;

import java.util.Comparator;

import org.eclipse.gef.EditPart;

/**
 * Compares two edit parts according to the number of their children.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class ChildrenComparator implements Comparator<EditPart> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(EditPart o1, EditPart o2) {
		return compare(o1.getChildren().size(), o2.getChildren().size());
	}

	private static int compare(int x, int y) {
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}

}
