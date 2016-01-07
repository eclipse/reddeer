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
package org.jboss.reddeer.swt.impl.ctab;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.api.CTabFolder;
import org.jboss.reddeer.swt.api.CTabItem;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all {@link CTabFolder} implementations
 * 
 * @author Lucia Jelinkova
 *
 */
public class AbstractCTabFolder extends AbstractWidget<org.eclipse.swt.custom.CTabFolder> implements CTabFolder {

	protected AbstractCTabFolder(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.custom.CTabFolder.class, referencedComposite, index, matchers);
	}
	
	protected AbstractCTabFolder(org.eclipse.swt.custom.CTabFolder swtWidget){
		super(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.CTabFolder#getSelection()
	 */
	@Override
	public CTabItem getSelection() {
		return new DefaultCTabItem(swtWidget.getSelection());
	}
}
