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
package org.eclipse.reddeer.swt.test.impl.scale;

import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.reddeer.swt.api.Scale;
import org.eclipse.reddeer.swt.api.Text;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.swt.impl.scale.DefaultScale;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.test.SWTLayerTestCase;
import org.junit.Test;
/**
 * Tests Scale implementation
 * @author vlado pakan
 *
 */
public class ScaleTest extends SWTLayerTestCase{
	private static final int[] SCALE_MAXIMUM = new int[]{100,200};
	private static final int[] SCALE_MINIMUM = new int[]{10,110};
	private static final int[] SCALE_INIT_VALUE = new int[]{50,150};
	private Text selectionText = null;
	private SelectionListener selectionListener;
	private org.eclipse.swt.widgets.Text txSelection;

	@Override
	protected void createControls(Shell shell){
		this.selectionListener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				org.eclipse.swt.widgets.Scale selectedScale = (org.eclipse.swt.widgets.Scale)arg0.widget;
				txSelection.setText(String.valueOf(selectedScale.getSelection()));
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
			}
		};
		for (int index = 0 ; index < ScaleTest.SCALE_INIT_VALUE.length ; index++){
			org.eclipse.swt.widgets.Scale scale = new org.eclipse.swt.widgets.Scale(shell, SWT.BORDER);
			scale.setMaximum(ScaleTest.SCALE_MAXIMUM[index]);
			scale.setMinimum(ScaleTest.SCALE_MINIMUM[index]);
			scale.setIncrement(1);
			scale.setSelection(ScaleTest.SCALE_INIT_VALUE[index]);
			scale.addSelectionListener(selectionListener);
		}
		txSelection = new org.eclipse.swt.widgets.Text(shell,SWT.BORDER);
		txSelection.setText("<value of selected scale>");
	}

	@Test
	public void findScaleByIndex(){
		int index = 1;
		Scale scale = new DefaultScale(index);
		assertTrue("Wrong scale widget was found",
			scale.getMinimum() == ScaleTest.SCALE_MINIMUM[index]);
	}
	@Test(expected=CoreLayerException.class)
	public void findNonExistingScaleByIndex(){
		int index = 4;
		new DefaultScale(index);
	}
	@Test
	public void getMinimum(){
		int index = 0;
		Scale scale = new DefaultScale(index);
		int minimum = scale.getMinimum();
		assertTrue("Wrong scale minimum value. Was " + minimum
				+ " expected was " + ScaleTest.SCALE_MINIMUM[index],
			minimum == ScaleTest.SCALE_MINIMUM[index]);
	}
	@Test
	public void getMaximum(){
		int index = 1;
		Scale scale = new DefaultScale(index);
		int maximum = scale.getMaximum();
		assertTrue("Wrong scale maximum value. Was " + maximum
				+ " expected was " + ScaleTest.SCALE_MINIMUM[index],
			maximum == ScaleTest.SCALE_MAXIMUM[index]);
	}
	@Test
	public void getSelection(){
		int index = 1;
		Scale scale = new DefaultScale(index);
		int selection = scale.getSelection();
		assertTrue("Wrong scale selection value. Was " + selection
				+ " expected was " + ScaleTest.SCALE_INIT_VALUE[index],
			selection == ScaleTest.SCALE_INIT_VALUE[index]);
	}
	@Test
	public void setSelection(){
		Scale scale = new DefaultScale();
		int newSelection = 80;
		scale.setSelection(newSelection);
		int selection = scale.getSelection();
		assertTrue("Scale selection value was not set. It was " + selection
				+ " expected was " + newSelection,
			selection == newSelection);
		// Check if selection event was fired
		assertTrue("Selection Event was not fired properly",
				getSelectionText().getText().equals(String.valueOf(newSelection)));
	}
	private Text getSelectionText(){
		if (this.selectionText == null){
			this.selectionText = new DefaultText(0);
		}
		return this.selectionText;
	}
}
