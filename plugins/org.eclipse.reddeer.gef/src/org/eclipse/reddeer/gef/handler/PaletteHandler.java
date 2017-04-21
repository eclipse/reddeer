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
package org.eclipse.reddeer.gef.handler;

import java.util.List;

import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.gef.finder.PaletteEntryFinder;

/**
 * Contains methods that handle UI operations on {@link org.eclipse.gef.ui.palette.PaletteViewer} and
 * {@link org.eclipse.gef.ui.palette.PaletteViewer}.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class PaletteHandler {

	protected final Logger log = Logger.getLogger(this.getClass());
	private static PaletteHandler instance;
	
	/**
	 * Gets instance of PaletteHandler.
	 * 
	 * @return instance of PaletteHandler
	 */
	public static PaletteHandler getInstance(){
		if(instance == null){
			instance = new PaletteHandler();
		}
		return instance;
	}

	/**
	 * Returns label of the specified palette entry.
	 * 
	 * @param paletteEntry
	 *            Palette entry
	 * @return Label
	 */
	public String getLabel(final PaletteEntry paletteEntry) {
		return Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return paletteEntry.getLabel();
			}
		});
	}

	/**
	 * Activates a given tool entry.
	 * 
	 * @param paletteViewer
	 *            Palette viewer
	 * @param toolEntry
	 *            Tool entry
	 */
	public void activateTool(final PaletteViewer paletteViewer, final ToolEntry toolEntry) {
		Display.asyncExec(new Runnable() {
			@Override
			public void run() {
				paletteViewer.setActiveTool(toolEntry);
			}
		});
	}

	/**
	 * Returns active tool entry of the specified palette viewer.
	 * 
	 * @param paletteViewer
	 *            Palette viewer
	 * @return Active tool entry
	 */
	public ToolEntry getActiveTool(final PaletteViewer paletteViewer) {
		return Display.syncExec(new ResultRunnable<ToolEntry>() {
			@Override
			public ToolEntry run() {
				return paletteViewer.getActiveTool();
			}
		});
	}

	/**
	 * Returns all palette entries for a given palette viewer which fulfill the specified matcher.
	 * 
	 * @param paletteViewer
	 *            Palette viewer
	 * @param matcher
	 *            Matcher
	 * @return List of palette entries
	 */
	public List<PaletteEntry> getPaletteEntries(final PaletteViewer paletteViewer, final Matcher<PaletteEntry> matcher) {
		return Display.syncExec(new ResultRunnable<List<PaletteEntry>>() {
			@Override
			public List<PaletteEntry> run() {
				PaletteRoot paletteRoot = paletteViewer.getPaletteRoot();
				return new PaletteEntryFinder().find(paletteRoot, matcher);
			}
		});
	}
}
