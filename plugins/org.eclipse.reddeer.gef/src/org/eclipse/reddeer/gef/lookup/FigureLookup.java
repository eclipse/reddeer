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
package org.eclipse.reddeer.gef.lookup;

import java.util.List;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPartViewer;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.gef.GEFLayerException;
import org.eclipse.reddeer.gef.finder.FigureFinder;

/**
 * Lookup for {@link org.eclipse.draw2d.IFigure}.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class FigureLookup {

	protected final Logger log = Logger.getLogger(this.getClass());

	private static FigureLookup instance;

	private FigureLookup() {

	}

	/**
	 * Gets the single instance of FigureLookup.
	 *
	 * @return single instance of FigureLookup
	 */
	public static FigureLookup getInstance() {
		if (instance == null) {
			instance = new FigureLookup();
		}
		return instance;
	}

	/**
	 * Finds a figure which is fulfilled by the specified matcher at a given index. The figure is searched in the active
	 * editor.
	 * 
	 * @param matcher
	 *            Figure matcher
	 * @param index
	 *            Index
	 * @return Figure
	 */
	public IFigure findFigure(Matcher<?> matcher, int index) {
		return findFigure(ViewerLookup.getInstance().findGraphicalViewer(), matcher, index);
	}

	/**
	 * Finds a figure which is fulfilled by the specified matcher at a given index. The figure is searched in a given
	 * viewer.
	 * 
	 * @param viewer
	 *            Edit part viewer
	 * @param matcher
	 *            Figure matcher
	 * @param index
	 *            Index
	 * @return Figure
	 */
	public IFigure findFigure(EditPartViewer viewer, Matcher<?> matcher, int index) {
		FigureCanvas canvas = (FigureCanvas) viewer.getControl();
		return findFigure(canvas.getContents(), matcher, index);
	}

	/**
	 * Finds a figure which is fulfilled by the specified matcher at a given index. The figure is searched inside the
	 * specified figure parent.
	 * 
	 * @param parent
	 *            Figure parent
	 * @param matcher
	 *            Figure matcher
	 * @param index
	 *            Index
	 * @return Figure
	 */
	public IFigure findFigure(IFigure parent, Matcher<?> matcher, int index) {
		List<IFigure> figures = new FigureFinder().find(parent, matcher);
		if (figures.size() <= index) {
			throw new GEFLayerException("Cannot find figure with matcher " + matcher + " at " + index);
		}
		return figures.get(index);
	}

}
