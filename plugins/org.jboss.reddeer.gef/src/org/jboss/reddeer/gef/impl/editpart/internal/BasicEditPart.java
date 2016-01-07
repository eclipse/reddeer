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
package org.jboss.reddeer.gef.impl.editpart.internal;

import org.eclipse.gef.EditPart;
import org.jboss.reddeer.gef.impl.editpart.AbstractEditPart;

/**
 * Internal implementation of EditPart
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class BasicEditPart extends AbstractEditPart {

	/**
	 * Instantiates a new basic edit part.
	 *
	 * @param editPart the edit part
	 */
	public BasicEditPart(EditPart editPart) {
		super(editPart);
	}

}
