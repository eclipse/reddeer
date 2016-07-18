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
package org.jboss.reddeer.gef.impl.editpart;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.gef.api.EditPart;
import org.jboss.reddeer.gef.comparator.ChainedComparator;
import org.jboss.reddeer.gef.comparator.ChildrenComparator;
import org.jboss.reddeer.gef.comparator.PositionComparator;
import org.jboss.reddeer.gef.handler.EditPartHandler;
import org.jboss.reddeer.gef.handler.ViewerHandler;
import org.jboss.reddeer.gef.lookup.EditPartLookup;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.impl.text.DefaultText;

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
	 * @param editPart
	 */
	public AbstractEditPart(org.eclipse.gef.EditPart editPart) {
		this.editPart = editPart;
	}

	/**
	 * Constructs edit part which fulfills a given matcher.
	 * 
	 * @param editPart
	 */
	public AbstractEditPart(Matcher<org.eclipse.gef.EditPart> matcher) {
		this(matcher, 0);
	}

	/**
	 * Constructs edit part which fulfills a given matcher at the specified index.
	 * 
	 * @param editPart
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
		WidgetHandler.getInstance().notify(SWT.DefaultSelection, inputText.getSWTWidget());
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
