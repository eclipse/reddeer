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
package org.jboss.reddeer.gef.matcher;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Returns true if an object is an instance of selectable {@link org.eclipse.gef.EditPart}.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class IsEditPart extends BaseMatcher<EditPart> {

	/* (non-Javadoc)
	 * @see org.hamcrest.Matcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(Object obj) {
		return obj instanceof GraphicalEditPart && ((GraphicalEditPart) obj).isSelectable();
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description desc) {
		desc.appendText("is EditPart");
	}

}
