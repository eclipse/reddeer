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
package org.jboss.reddeer.core.handler;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Control} widgets.
 * @author rawagner
 *
 */
public class ControlHandler {
	
	private static ControlHandler instance;
	
	private ControlHandler(){};
	
	/**
	 * Gets instance of ControlHandler
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

		if (control instanceof Shell) {
			ShellHandler.getInstance().setFocus((Shell) control);
		} else if (control instanceof Button) {
			ButtonHandler.getInstance().setFocus((Button)control);
		} else {
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					control.setFocus();
				}
			});
		}
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

}
