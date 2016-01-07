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
 * Returns true if an object is an instance of a given instance.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class IsEditPartOfInstance extends BaseMatcher<EditPart> {

	private String instance;

	/**
	 * Constructs the matcher with given instance name. You can specify only a simple name or the whole canonical name.
	 * 
	 * @param instance
	 *            Instance name
	 */
	public IsEditPartOfInstance(String instance) {
		this.instance = instance;
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.Matcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(Object obj) {
		if (obj instanceof GraphicalEditPart && ((GraphicalEditPart) obj).isSelectable()) {
			String name = obj.getClass().getSimpleName();
			String canonicalName = obj.getClass().getCanonicalName();
			return name.equals(instance) || canonicalName.equals(instance);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description desc) {
		desc.appendText("is EditPart of instance '" + instance + "'");
	}

}
