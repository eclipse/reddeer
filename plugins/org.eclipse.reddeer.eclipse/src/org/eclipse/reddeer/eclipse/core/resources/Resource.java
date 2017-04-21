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
package org.eclipse.reddeer.eclipse.core.resources;

import java.util.List;

import org.eclipse.reddeer.common.adaptable.RedDeerAdaptable;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.eclipse.ui.dialogs.PropertyDialog;
import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * API for resources in various views (explorers, navigators.
 * 
 * @author mlabuda@redhat.com
 * @since 2.0
 */
public interface Resource extends RedDeerAdaptable<Resource> {

	/////////////////////////////////////
	// RESOURCE TREE ITEM MANIPULATION //
	/////////////////////////////////////

	/**
	 * Gets encapsulated {@link TreeItem} representing the resource.
	 * 
	 * @return encapsulated tree item
	 */
	TreeItem getTreeItem();

	/**
	 * Opens tree item of the resource.
	 */
	void open();

	/**
	 * Selects tree item of the resource.
	 */
	void select();

	/**
	 * Finds out whether tree item of the resource is selected or not.
	 * 
	 * @return true if the tree item is selected, false otherwise
	 */
	boolean isSelected();

	/**
	 * Refreshes the resource.
	 */
	void refresh();

	/**
	 * Expands the explorer item.
	 */
	void expand();

	/**
	 * Detects if the explorer item is expanded.
	 * 
	 * @return true if the explorer item is expanded, false otherwise
	 */
	boolean isExpanded();

	/**
	 * Collapses the explorer item.
	 */
	void collapse();

	/**
	 * Gets whole text of the resource.
	 * 
	 * @return text of the resource
	 */
	String getText();

	/**
	 * Gets resource specified by path from current resource. Method go through
	 * whole hierarchy and on each layer at first try to find resource specified
	 * by part of the path as it is (whole text). If there is no such resource,
	 * then resource is looked up by non-decorated text representing this
	 * resource. If there is more than one resource in this step containing same
	 * non-styled text, then EclipseLayerException is thrown.
	 *
	 * @param path
	 *            path to the specific resource
	 * @return resource item found on the specified path
	 */
	Resource getResource(String... path);

	/**
	 * Finds out whether the resource contains resource specified by path or
	 * not.
	 * 
	 * @param path
	 *            of a resource to be looked up
	 * @return true if the resource contains a resource specified by path, false
	 *         otherwise
	 */
	boolean containsResource(String... path);
	
	/**
	 * Gets direct children of the resource.
	 * 
	 * @return children of the resource.
	 */
	List<Resource> getChildren();
	
	/**
	 * Gets name of the resource without decorators.
	 * 
	 * @return name of the resource without decorators
	 */
	String getName();

	/**
	 * Gets decorated parts of the resource text.
	 * 
	 * @return String[] of decorated texts on the resource
	 */
	String[] getDecoratedParts(); 
	
	/////////////////////////////////////
	// EXECUTE ACTIONS ON THE RESOURCE //
	/////////////////////////////////////
	
	/**
	 * Deletes the resource. It is deleted from everywhere.
	 */
	void delete();
	
	/**
	 * Runs the resource as JUnit Test and waits until it is finished. Before it is
	 * run settings for activating a console are turned off. At the end the
	 * settings are set back as they was.
	 */
	void runAsJUnitTest();
	
	/**
	 * Runs the resource as JUnit Test and waits until it is finished in a given
	 * timeout. Before it is run settings for activating a console are turned
	 * off. At the end the settings are set back as they was.
	 * 
	 * @param timeout
	 *            timeout to wait for JUnit tests to finish
	 */
	void runAsJUnitTest(TimePeriod timeout); 
	
	/**
	 * Runs resource with the specified launcher.
	 * 
	 * @param launcher
	 *            launcher to launch the resource
	 */
	void runAs(String launcher);
	
	/**
	 * Debugs the resource with the specified launcher.
	 * 
	 * @param launcher
	 *            launcher to debug the resource
	 */
	void debugAs(String launcher);
	
	/////////////////////////////////////////////
	// METHODS RELATED TO VIEW OF THE RESOURCE //
	/////////////////////////////////////////////

	/**
	 * Activates View containing the resource.
	 */
	void activateWrappingView();

	/**
	 * Gets title of view containing the resource.
	 * 
	 * @return title of view containing the resource
	 */
	public String getTitleOfWrappingView();
	
	/**
	 * Opens properties dialog for the resource
	 * @return properties dialog for the resource
	 */
	PropertyDialog openProperties();
}
