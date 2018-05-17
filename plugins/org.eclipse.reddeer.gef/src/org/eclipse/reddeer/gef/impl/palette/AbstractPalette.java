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
package org.eclipse.reddeer.gef.impl.palette;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.eclipse.reddeer.gef.GEFLayerException;
import org.eclipse.reddeer.gef.api.Palette;
import org.eclipse.reddeer.gef.handler.PaletteHandler;
import org.eclipse.reddeer.gef.matcher.IsToolEntry;
import org.eclipse.reddeer.gef.matcher.IsToolEntryWithLabel;
import org.eclipse.reddeer.gef.matcher.IsToolEntryWithParent;

/**
 * Abstract class form Palette implementation
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public abstract class AbstractPalette implements Palette {

	protected PaletteViewer paletteViewer;
	protected PaletteHandler paletteHandler;

	/**
	 * Constructor for AbstractPalette. You nedd to specify palette viewer.
	 * 
	 * @param paletteViewer
	 *            Palette viewer
	 */
	public AbstractPalette(PaletteViewer paletteViewer) {
		this.paletteViewer = paletteViewer;
		this.paletteHandler = PaletteHandler.getInstance();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.gef.api.Palette#activateTool(java.lang.String)
	 */
	@Override
	public void activateTool(String label) {
		activateTool(label, null);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.gef.api.Palette#activateTool(java.lang.String, java.lang.String)
	 */
	@Override
	public void activateTool(String tool, String group) {
		List<Matcher<? super PaletteEntry>> matchers = new ArrayList<Matcher<? super PaletteEntry>>();
		matchers.add(new IsToolEntryWithLabel(tool));
		if (group != null) {
			matchers.add(new IsToolEntryWithParent(group));
		}
		Matcher<PaletteEntry> matcher = new AllOf<PaletteEntry>(matchers);
		activateTool(matcher, 0);
	}

	/**
	 * Activates a tool with a given matcher at specified index.
	 *
	 * @param matcher            Matcher
	 * @param index            Index
	 */
	protected void activateTool(Matcher<PaletteEntry> matcher, int index) {
		List<PaletteEntry> entries = paletteHandler.getPaletteEntries(paletteViewer, matcher);
		if (entries.size() <= index) {
			throw new GEFLayerException("Cannot find tool entry with " + matcher + " at index " + index);
		}
		ToolEntry toolEntry = (ToolEntry) entries.get(index);
		paletteHandler.activateTool(paletteViewer, toolEntry);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.gef.api.Palette#getActiveTool()
	 */
	@Override
	public String getActiveTool() {
		ToolEntry activeTool = paletteHandler.getActiveTool(paletteViewer);
		return paletteHandler.getLabel(activeTool);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.gef.api.Palette#getTools()
	 */
	@Override
	public List<String> getTools() {
		List<PaletteEntry> entries = paletteHandler.getPaletteEntries(paletteViewer, new IsToolEntry());
		List<String> tools = new ArrayList<String>();
		for (PaletteEntry entry : entries) {
			tools.add(paletteHandler.getLabel(entry));
		}
		return tools;
	}

}
