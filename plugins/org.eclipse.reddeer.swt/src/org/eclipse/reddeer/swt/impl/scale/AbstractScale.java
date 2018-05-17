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
package org.eclipse.reddeer.swt.impl.scale;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.swt.api.Scale;
import org.eclipse.reddeer.core.handler.ScaleHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

/**
 * Abstract class for each Scale implementation
 * @author Vlado Pakan
 *
 */
public abstract class AbstractScale extends AbstractControl<org.eclipse.swt.widgets.Scale> implements Scale {

	protected AbstractScale(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.Scale.class, refComposite, index, matchers);
	}
	
	protected AbstractScale(org.eclipse.swt.widgets.Scale widget){
		super(widget);
	}
	
	/**
	 * See {@link Scale}.
	 *
	 * @return the minimum
	 */
	@Override
	public int getMinimum() {
		return ScaleHandler.getInstance().getMinimum(this.getSWTWidget());
	}
	
	/**
	 * See {@link Scale}.
	 *
	 * @return the maximum
	 */
	@Override
	public int getMaximum() {
		return ScaleHandler.getInstance().getMaximum(this.getSWTWidget());
	}
	
	/**
	 * See {@link Scale}.
	 *
	 * @return the selection
	 */
	@Override
	public int getSelection() {
		return ScaleHandler.getInstance().getSelection(this.getSWTWidget());
	}
	
	/**
	 * See {@link Scale}.
	 *
	 * @param value the new selection
	 */
	@Override
	public void setSelection(int value) {
		ScaleHandler.getInstance().setSelection(this.getSWTWidget(), value);		
	}
}
