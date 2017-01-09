/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.core.resources;

import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Represents a project with maven nature inside of explorer view.
 * 
 * @author mlabuda@redhat.com
 * @since 2.0
 */
public class MavenProject extends DefaultProject {

	public MavenProject(TreeItem item) {
		super(item);
	}

	@Override
	public String[] getNatureIds() {
		return new String[] { "org.eclipse.m2e.core.maven2Nature" };
	}
}