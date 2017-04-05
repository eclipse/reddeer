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

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.lookup.ShellLookup;

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
		checkModalShells(control);
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
		checkModalShells(control);
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
	
	public Composite getParent(final org.eclipse.swt.widgets.Control control){
		return Display.syncExec(new ResultRunnable<Composite>() {

			@Override
			public Composite run() {
				return control.getParent();
			}
		});
	}
	
	/**
	 * Checks if modal shell that blocks shell in which specified control is located exists 
	 * and throws exception if it does
	 * @param control which shell should be checked if it is blocked by some other shell
	 * @throws CoreLayerException if modal shell that blocks current shell exists
	 */
	public void checkModalShells(final org.eclipse.swt.widgets.Control control){
		if(!isDisposed(control)){
			Shell shell = getBlockingModalShell(control);
			if(shell!= null){
				throw new CoreLayerException("Unable to execute action because modal shell '"+
						ShellHandler.getInstance().getText(shell)+"' exists");
			}
		}
	}
	
	/**
	 * Gets modal shell that blocks shell in which specified control is located
	 * @param control which shell should be checked if it is blocked by some other shell
	 * @return modal shell that blocks shell in which specified control is located or null
	 */
	public Shell getBlockingModalShell(final org.eclipse.swt.widgets.Control control){
		//The PRIMARY_MODAL style allows an instance to block input to its parent
		//The APPLICATION_MODAL style allows an instance to block input to every other shell in the display
		//SYSTEM_MODAL is the same as APPLICATION_MODAL because SYSTEM_MODAL is not supported in most (any?)desktop environments
		Shell[] allShells = ShellLookup.getInstance().getShells();
		Shell controlShell = getShell(control);
		List<Shell> allAncestorShells = ShellHandler.getInstance().getAllAncestorShells(controlShell);
		for(Shell shell: allShells){
			if(!isDisposed(shell)){
				int style = getStyle(shell);
				if(!shell.equals(controlShell)){ //check shells which are not parent of given control
					// check shells which are not among parents of given control
					if(!allAncestorShells.contains(shell)){
						if((style & SWT.APPLICATION_MODAL)!=0 || (style & SWT.SYSTEM_MODAL)!=0){
							return shell;
						}
					}
					if((style & SWT.PRIMARY_MODAL) != 0){
						Composite compositeParent = getParent(shell);
						if(compositeParent != null && !isDisposed(compositeParent)){
							//only direct parent shell is blocked by PRIMARY_MODAL
							Shell shellParent = getShell(compositeParent);
							if(shellParent != null && !isDisposed(shellParent) && shellParent.equals(controlShell)){
								return shell;
							}
						}
					}
				}
			}
			
		}
		return null;
	}
	

}
