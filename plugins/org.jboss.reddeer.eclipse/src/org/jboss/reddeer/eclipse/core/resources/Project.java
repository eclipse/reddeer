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

/**
 * API for Project manipulation. Projects are located in various explorers.
 * 
 * @author mlabuda@redhat.com
 * @since 2.0
 */
public interface Project extends Resource {

	/**
	 * Gets nature ids of specific project type. E.g. maven nature or java
	 * nature etc.
	 * 
	 * @return nature ids of specific project type
	 */
	String[] getNatureIds();

	/**
	 * Gets project item of the project specified by path. Method go through
	 * whole hierarchy and on each layer at first try to find resource specified
	 * by part of the path as it is (whole text). If there is no such resource,
	 * then resource is looked up by non-decorated text representing this
	 * project item. If there is more than one project item in this step
	 * containing same non-styled text, then EclipseLayerException is thrown.
	 *
	 * @param path
	 *            path to the specific project item
	 * @return project item found on the specified path
	 */
	ProjectItem getProjectItem(String... path);

	/**
	 * Deletes project. If parameter deleteFromFileSystem is set to true,
	 * project is also deleted from file system. Otherwise it is kept on file
	 * system and only removed from an explorer.
	 * 
	 * @param deleteFromFileSystem
	 *            whether project should be deleted from file system, or kept
	 */
	void delete(boolean deleteFromFileSystem);
}
