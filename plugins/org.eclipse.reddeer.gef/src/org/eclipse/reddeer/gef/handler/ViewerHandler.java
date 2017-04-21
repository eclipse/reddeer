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

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.swt.SWT;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.handler.WidgetHandler;
import org.eclipse.reddeer.gef.api.Palette;
import org.eclipse.reddeer.gef.finder.EditPartFinder;
import org.eclipse.reddeer.gef.impl.palette.internal.BasicPalette;

/**
 * Handler for {@link org.eclipse.gef.GraphicalViewer}.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class ViewerHandler {

	protected final Logger log = Logger.getLogger(this.getClass());
	private static ViewerHandler instance;
	
	/**
	 * Gets instance of ViewerHandler.
	 * 
	 * @return instance of ViewerHandler
	 */
	public static ViewerHandler getInstance(){
		if(instance == null){
			instance = new ViewerHandler();
		}
		return instance;
	}

	/**
	 * Returns palette for a given graphical viewer.
	 * 
	 * @param viewer
	 *            graphical viewer
	 * @return Palette
	 */
	public Palette getPalette(final GraphicalViewer viewer) {
		PaletteViewer paletteViewer = Display.syncExec(new ResultRunnable<PaletteViewer>() {

			@Override
			public PaletteViewer run() {
				return viewer.getEditDomain().getPaletteViewer();
			}
		});
		return new BasicPalette(paletteViewer);
	}

	/**
	 * Returns all editor parts in a given graphical viewer.
	 * 
	 * @param viewer
	 *            Graphical viewer
	 * @return List of edit parts
	 */
	public List<EditPart> getEditParts(final GraphicalViewer viewer) {
		return getEditParts(viewer, new Any());
	}

	/**
	 * Returns all editor parts in a given graphical viewer which fulfill the specified matcher.
	 * 
	 * @param viewer
	 *            Graphical viewer
	 * @param matcher
	 *            Matcher
	 * @return List of edit parts
	 */
	public List<EditPart> getEditParts(final EditPartViewer viewer, final Matcher<EditPart> matcher) {
		return Display.syncExec(new ResultRunnable<List<EditPart>>() {
			@Override
			public List<EditPart> run() {
				EditPart root = viewer.getContents();
				return new EditPartFinder().find(root, matcher);
			}

		});
	}

	/**
	 * Clicks on a given at the specified coordinates.
	 *
	 * @param viewer the viewer
	 * @param x            X-axis
	 * @param y            Y-axis
	 */
	public void click(final EditPartViewer viewer, final int x, final int y) {
		log.info("Click at [" + x + ", " + y + "]");
		FigureCanvas figureCanvas = getFigureCanvas(viewer);
		WidgetHandler.getInstance().notifyItemMouse(SWT.MouseMove, 0, figureCanvas, null, x, y, 0);
		WidgetHandler.getInstance().notifyItemMouse(SWT.MouseDown, 0, figureCanvas, null, x, y, 1);
		WidgetHandler.getInstance().notifyItemMouse(SWT.MouseUp, 0, figureCanvas, null, x, y, 1);
	}

	/**
	 * Gets the figure canvas.
	 *
	 * @param viewer the viewer
	 * @return the figure canvas
	 */
	public FigureCanvas getFigureCanvas(final EditPartViewer viewer) {
		return (FigureCanvas) viewer.getControl();
	}

	private class Any extends BaseMatcher<EditPart> {

		@Override
		public boolean matches(Object item) {
			return true;
		}

		@Override
		public void describeTo(Description description) {

		}

	}

}
