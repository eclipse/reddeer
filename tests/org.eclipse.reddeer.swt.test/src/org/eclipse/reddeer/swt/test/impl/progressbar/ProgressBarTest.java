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
package org.eclipse.reddeer.swt.test.impl.progressbar;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.swt.impl.progressbar.HorizontalProgressBar;
import org.eclipse.reddeer.swt.impl.progressbar.IndeterminateProgressBar;
import org.eclipse.reddeer.swt.impl.progressbar.VerticalProgressBar;
import org.eclipse.reddeer.swt.test.SWTLayerTestCase;
import org.junit.Test;

public class ProgressBarTest extends SWTLayerTestCase{

	private ProgressBar pbVertical;
	private ProgressBar pbHorizontal;
	
	@Override
	protected void createControls(Shell shell){
		pbVertical = new ProgressBar(shell, SWT.VERTICAL);
		pbVertical.setMinimum(0);
		pbVertical.setMaximum(100);
		pbVertical.setSelection(15);
		pbVertical.setSelection(50);
		pbHorizontal = new ProgressBar(shell, SWT.HORIZONTAL);
		pbHorizontal.setMaximum(200);
		pbHorizontal.setMinimum(100);
		pbHorizontal.setSelection(150);
		new ProgressBar(shell, SWT.HORIZONTAL | SWT.INDETERMINATE);
	}

	@Test
	public void verticalProgressBarTest(){
		VerticalProgressBar vpb = new VerticalProgressBar();
		new VerticalProgressBar(0);
		assertEquals(0, vpb.getMinimum());
		assertEquals(100, vpb.getMaximum());
		assertEquals(50, vpb.getSelection());
		assertEquals(SWT.NORMAL, vpb.getState());
	}
	
	@Test(expected=CoreLayerException.class)
	public void nonExistingVerticalProgressBarTest(){
		new VerticalProgressBar(1);
	}
	
	@Test
	public void horizontalProgressBarTest(){
		HorizontalProgressBar hpb = new HorizontalProgressBar();
		new HorizontalProgressBar(0);
		new HorizontalProgressBar(1);
		assertEquals(100, hpb.getMinimum());
		assertEquals(200, hpb.getMaximum());
		assertEquals(150, hpb.getSelection());
	}
	
	@Test(expected=CoreLayerException.class)
	public void nonExistinghorizontalProgressBarTest(){
		new HorizontalProgressBar(2);
	}
	
	@Test
	public void IndeterminateProgressBarTest(){
		new IndeterminateProgressBar();
		new IndeterminateProgressBar(0);
	}
	
	@Test(expected=CoreLayerException.class)
	public void noonExistingIndeterminateProgressBarTest(){
		new IndeterminateProgressBar(1);
	}
	
}