/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.core.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
/**
 * Contains methods for handling UI operations on {@link Control} widgets.
 * @author rawagner
 *
 */
public class ControlHandler extends WidgetHandler{
	
	private static ControlHandler instance;
	
	/**
	 * Gets instance of ControlHandler.
	 * 
	 * @return instance of ControlHandler
	 */
	public static ControlHandler getInstance(){
		if(instance == null){
			instance = new ControlHandler();
		}
		return instance;
	}
	
	/**
	 * Checks if control is enabled
	 * @param control to handle
	 * @return true if control is enabled, false otherwise
	 */
	public boolean isEnabled(Control control){
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return control.isEnabled();
			}
		});
	}
	
	/**
	 * Checks if control is visible
	 * @param control to handle
	 * @return true if control is visible, false otherwise
	 */
	public boolean isVisible(Control control){
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return control.isVisible();
			}
		});
		
	}
	
	/**
	 * Sets focus to specified control
	 *
	 * @param control to handle
	 */
	public void setFocus(final Control control) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				control.setFocus();
			}
		});
	}
	
	/**
	 * Checks if control is focused
	 * @param control to handle
	 * @return true if control is focused, false otherwise
	 */
	public boolean isFocusControl(Control control){
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return control.isFocusControl();
			}
		});
	}
	
	/**
	 * Gets tooltip text of control
	 * @param control to get tooltip text from
	 * @return tooltip text of spepcified control
	 */
	public String getToolTipText(Control control){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return control.getToolTipText();
			}
		});
	}
	
	/**
	 * Force focus to SWT Control. Use with caution
	 * @param control SWT Control
	 * @return true if control was focused, false otherwise
	 */
	public boolean forceFocus(final org.eclipse.swt.widgets.Control control) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				boolean ret = control.forceFocus();
				return ret;
			}
		});		
	}
	
	/**
	 * Gets control's shell
	 * @param control to handle
	 * @return control's shell
	 */
	public Shell getShell(final org.eclipse.swt.widgets.Control control){
		return Display.syncExec(new ResultRunnable<Shell>() {

			@Override
			public Shell run() {
				return control.getShell();
			}
		});
	}
	
	/**
	 * Gets parent of given control
	 * @param control to handle
	 * @return parent of given control
	 */
	public Composite getParent(final org.eclipse.swt.widgets.Control control){
		return Display.syncExec(new ResultRunnable<Composite>() {

			@Override
			public Composite run() {
				return control.getParent();
			}
		});
	}
	
	/**
	 * Gets menu of given control
 	 * @param control to handle
	 * @return menu of given control
	 */
	public Menu getMenu(final org.eclipse.swt.widgets.Control control) {
		//Send MenuDetect event. Some menus doesn't exist before that..
		notifyWidget(SWT.MenuDetect, control);
		return Display.syncExec(new ResultRunnable<Menu>() {

			@Override
			public Menu run() {
				return control.getMenu();
			}
		});
	}
	

}
