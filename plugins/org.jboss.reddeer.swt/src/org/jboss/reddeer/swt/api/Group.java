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
package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for group manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Group extends ReferencedComposite, Widget {
	
	/**
	 * Gets text of the group.
	 * 
	 * @since 0.4
	 * @return text of the group
	 */
	String getText();
	
	org.eclipse.swt.widgets.Group getSWTWidget();

}
