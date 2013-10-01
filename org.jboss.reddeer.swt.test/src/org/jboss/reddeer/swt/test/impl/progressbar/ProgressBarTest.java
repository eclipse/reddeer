package org.jboss.reddeer.swt.test.impl.progressbar;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.impl.progressbar.HorizontalProgressBar;
import org.jboss.reddeer.swt.impl.progressbar.IndeterminateProgressBar;
import org.jboss.reddeer.swt.impl.progressbar.VerticalProgressBar;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.junit.After;
import org.junit.Test;

public class ProgressBarTest extends RedDeerTest{

	private ProgressBar pbVertical;
	private ProgressBar pbHorizontal;
	private ProgressBar pbIndeterminate;
	
	@Override
	protected void setUp() {
		super.setUp();
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = new Shell(org.eclipse.swt.widgets.Display.getDefault());				
				shell.setLayout(new GridLayout());
				shell.setText("Testing shell");
				createControls(shell);
				shell.open();
				shell.setFocus();
			}
		});
	}
	
	protected void createControls(Shell shell){
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		pbVertical = new ProgressBar(shell, SWT.VERTICAL);
		pbVertical.setMinimum(0);
		pbVertical.setMaximum(100);
		pbVertical.setSelection(15);
		pbVertical.setSelection(50);
		pbHorizontal = new ProgressBar(shell, SWT.HORIZONTAL);
		pbHorizontal.setMaximum(200);
		pbHorizontal.setMinimum(100);
		pbHorizontal.setSelection(150);
		pbIndeterminate = new ProgressBar(shell, SWT.HORIZONTAL | SWT.INDETERMINATE);
	}

	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				for (Shell shell : org.jboss.reddeer.swt.
						util.Display.getDisplay().getShells()) {
					if (shell.getText().equals("Testing shell")) {
						shell.dispose();
						break;
					}
				}
			}
		});
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
		IndeterminateProgressBar ipb = new IndeterminateProgressBar();
		new IndeterminateProgressBar(0);
	}
	
	@Test(expected=SWTLayerException.class)
	public void noonExistingIndeterminateProgressBarTest(){
		new IndeterminateProgressBar(1);
	}
	
}