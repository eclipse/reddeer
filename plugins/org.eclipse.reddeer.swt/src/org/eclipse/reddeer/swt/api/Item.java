/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.api;

import org.eclipse.swt.graphics.Image;

/**
 * API for Item manipulation
 * @author rawagner
 *
 * @param <T>
 */
public interface Item<T extends org.eclipse.swt.widgets.Item> extends Widget<T>{
	
	/**
	 * Returns image of item
	 * @return image of item
	 */
	Image getImage();
	
	/**
	 * Gets text of item
	 * @return text of item
	 */
	String getText();
	
	/**
	 * Returns parent control
	 * @return parent control
	 */
	Control<?> getParentControl();
	

}
