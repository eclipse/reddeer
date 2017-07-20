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
package org.eclipse.reddeer.jface.test.preference;

import static org.junit.Assert.*;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.ui.PlatformUI;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class PreferenceDialogTest {
	
	static PreferenceDialog preference;
	
	@BeforeClass
	public static void openPreferenceDialog(){
		Display.asyncExec(new Runnable() {
			
			@Override
			public void run() {
				PreferenceManager mgr = new PreferenceManager();
				PreferenceNode node = new PreferenceNode("one", new MyPreferencePage());
				mgr.addToRoot(node);
				
				preference = new PreferenceDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), mgr);
				preference.open();
				
			}
		});
	}
	
	@AfterClass
	public static void closePreferences(){
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if(preference != null && preference.getShell() != null && !preference.getShell().isDisposed()){
					preference.close();
				}
			}
		});
	}
	
	@Test
	public void testCanFinish(){
		org.eclipse.reddeer.jface.preference.PreferenceDialog pd = new org.eclipse.reddeer.jface.preference.PreferenceDialog("Preferences");
		assertFalse(pd.canFinish());
		new PushButton("make valid").click();
		assertTrue(pd.canFinish());
		pd.ok();
	}
	
}
