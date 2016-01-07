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
package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.core.handler.MenuHandler;
import org.jboss.reddeer.core.lookup.MenuLookup;

/**
 * Abstract class for all Menu implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractMenu implements Menu {

	protected String[] path;
	protected Matcher<String>[] matchers;
	protected MenuLookup ml = MenuLookup.getInstance();
	protected MenuHandler mh = MenuHandler.getInstance();
	protected MenuItem menuItem = null;


	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Menu#select()
	 */
	@Override
	public abstract void select();
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Menu#isSelected()
	 */
	@Override
	public abstract boolean isSelected();
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Menu#getText()
	 */
	@Override
	public String getText() {
		throw new UnsupportedOperationException("not yet implemented");
	}
	
}
