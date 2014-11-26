package org.jboss.reddeer.swt.test.impl.progressbar;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.progressbar.HorizontalProgressBar;
import org.jboss.reddeer.swt.impl.progressbar.IndeterminateProgressBar;
import org.jboss.reddeer.swt.impl.progressbar.VerticalProgressBar;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
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
	
	@Test(expected=SWTLayerException.class)
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
	
	@Test(expected=SWTLayerException.class)
	public void nonExistinghorizontalProgressBarTest(){
		new HorizontalProgressBar(2);
	}
	
	@Test
	public void IndeterminateProgressBarTest(){
		new IndeterminateProgressBar();
		new IndeterminateProgressBar(0);
	}
	
	@Test(expected=SWTLayerException.class)
	public void noonExistingIndeterminateProgressBarTest(){
		new IndeterminateProgressBar(1);
	}
	
}