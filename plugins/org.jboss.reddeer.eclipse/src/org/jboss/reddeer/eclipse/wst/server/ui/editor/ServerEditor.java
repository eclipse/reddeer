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
package org.jboss.reddeer.eclipse.wst.server.ui.editor;

import org.jboss.reddeer.workbench.impl.editor.AbstractEditor;

/**
 * Represents the editor for server properties. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServerEditor extends AbstractEditor {

	/**
	 * Instantiates a new server editor.
	 *
	 * @param title the title
	 */
	public ServerEditor(String title) {
		super(title);
	}
}
