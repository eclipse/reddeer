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
package org.jboss.reddeer.eclipse.ui.perspectives;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.matcher.WithTextMatchers;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.YesButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;

/**
 * Abstract parent for each Perspective implementation
 * 
 * @author vlado pakan
 * 
 */
public abstract class AbstractPerspective {

	protected final Logger log = Logger.getLogger(this.getClass());

	private String perspectiveLabel;

	/**
	 * Constructs the perspective with a given label.
	 * 
	 * @param perspectiveLabel Perspective label
	 */
	public AbstractPerspective(String perspectiveLabel) {
		super();
		this.perspectiveLabel = perspectiveLabel;
		if (!isPerspectiveAvailable()){
			throw new EclipseLayerException("Perspective "+perspectiveLabel+" isn't available");
		}
	}

	/**
	 * Opens the perspective.
	 */
	public void open() {
		log.info("Open perspective: '" + getPerspectiveLabel() + "'");
		if (isOpened()){
			log.debug("Perspective '" + getPerspectiveLabel() + "' is already opened.");
		}
		else{
			log.debug("Tryyying to open perspective: '" + getPerspectiveLabel() + "'");
			new DefaultToolItem(new DefaultShell(),"Open Perspective").click();
			new DefaultShell("Open Perspective");
			DefaultTable table = new DefaultTable();
			try{
				// Try to select perspective label within available perspectives
				table.select(getPerspectiveLabel());
			} catch (CoreLayerException swtLayerException){
				// Try to select perspective label within available perspectives with "(default)" suffix
				table.select(getPerspectiveLabel() + " (default)");
			}
			new PushButton("OK").click();
		}
	}

	/**
	 * Reset.
	 */
	public void reset(){
		if (!isOpened()){
			throw new EclipseLayerException("Trying to reset perspective that is not open") ;
		}
		// Prior to Eclipse Mars path to Reset Perspective menu
		WithTextMatchers m = new WithTextMatchers(new RegexMatcher[] {
				new RegexMatcher("Window.*"),
				new RegexMatcher("Reset Perspective...")});
		Menu menu;
		try {
			menu = new ShellMenu(m.getMatchers());	
		} catch (CoreLayerException swtle) {
			// Try menu path for Mars and higher versions
			m = new WithTextMatchers(new RegexMatcher[] {
					new RegexMatcher("Window.*"),
					new RegexMatcher("Perspective.*"),
					new RegexMatcher("Reset Perspective...")});
			menu = new ShellMenu(m.getMatchers());
		}
		
		menu.select();
		new DefaultShell("Reset Perspective");
		new YesButton().click();
	}

	/**
	 * Returns perspective label.
	 * 
	 * @return Perspective label
	 */
	public String getPerspectiveLabel() {
		return perspectiveLabel;
	}

	/**
	 * Returns whether the perspective is open.
	 * 
	 * @return Whether the perspective is open
	 */
	public boolean isOpened(){
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return getActivePerspective().getLabel().equals(perspectiveLabel);
			}
		});
	}

	private boolean isPerspectiveAvailable(){
		IPerspectiveDescriptor perspective = PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithLabel(perspectiveLabel);
		if (perspective == null){
			return false;
		}
		return true;
	}

	private IPerspectiveDescriptor getActivePerspective(){
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective();
	}
}
