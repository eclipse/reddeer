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
package org.eclipse.reddeer.gef.impl.editpart;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.handler.WidgetHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.gef.api.EditPart;
import org.eclipse.reddeer.gef.comparator.ChainedComparator;
import org.eclipse.reddeer.gef.comparator.ChildrenComparator;
import org.eclipse.reddeer.gef.comparator.PositionComparator;
import org.eclipse.reddeer.gef.handler.EditPartHandler;
import org.eclipse.reddeer.gef.handler.ViewerHandler;
import org.eclipse.reddeer.gef.lookup.EditPartLookup;
import org.eclipse.reddeer.swt.api.Text;
import org.eclipse.reddeer.swt.impl.text.DefaultText;

/**
 * Abstract class for EditPart implementation
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public abstract class AbstractEditPart implements EditPart, ReferencedComposite {

	protected org.eclipse.gef.GraphicalViewer viewer;
	protected org.eclipse.gef.EditPart editPart;

	/**
	 * Constructs edit part from {@link org.eclipse.gef.EditPart}.
	 * 
	 * @param editPart edit part
	 */
	public AbstractEditPart(org.eclipse.gef.EditPart editPart) {
		this.editPart = editPart;
	}

	/**
	 * Constructs edit part which fulfills a given matcher.
	 * 
	 * @param matcher matcher to match edit part
	 */
	public AbstractEditPart(Matcher<org.eclipse.gef.EditPart> matcher) {
		this(matcher, 0);
	}

	/**
	 * Constructs edit part which fulfills a given matcher at the specified index.
	 * 
	 * @param matcher matcher to match edit part
	 * @param index index of edit part
	 */
	public AbstractEditPart(Matcher<org.eclipse.gef.EditPart> matcher, int index) {
		this(EditPartLookup.getInstance().findEditPart(matcher, index,
				new ChainedComparator<org.eclipse.gef.EditPart>(new ChildrenComparator(), new PositionComparator())));
	}

	@Override
	public void select() {
		EditPartHandler.getInstance().select(editPart);
	}

	@Override
	public void click() {
		Rectangle bounds = getFigure().getBounds();
		final Rectangle rec = bounds.getCopy();
		getFigure().translateToAbsolute(rec);
		int x = rec.x + rec.width / 2;
		int y = rec.y + rec.height / 2;
		ViewerHandler.getInstance().click(editPart.getViewer(), x, y);
	}

	@Override
	public void setLabel(String text) {
		EditPartHandler.getInstance().select(editPart);
		EditPartHandler.getInstance().directEdit(editPart);
		Text inputText = new DefaultText(this, 0);
		inputText.setText(text);
		WidgetHandler.getInstance().notifyWidget(SWT.DefaultSelection, inputText.getSWTWidget());
	}

	@Override
	public Control getControl() {
		return editPart.getViewer().getControl();
	}

	@Override
	public org.eclipse.gef.EditPart getGEFEditPart() {
		return editPart;
	}

	protected IFigure getFigure() {
		return EditPartHandler.getInstance().getFigure(editPart);
	}

}
