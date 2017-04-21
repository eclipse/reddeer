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
 * API for label manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Label extends Control<org.eclipse.swt.widgets.Label> {

	/**
	 * Returns the text of the label.
	 * 
	 * @return text of the label
	 */
	String getText();
	
	/**
	 * Returns label image
	 * @return image of the label
	 */
	Image getImage();
}
