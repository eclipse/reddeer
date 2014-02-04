package org.jboss.reddeer.swt.test.toolitem;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.junit.After;
import org.junit.Test;

public class ToolItemTest extends RedDeerTest{

	private final String TOOLTIP="PUSH tooltip";
	
	@Override
	public void setUp() {
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
	
	private void createControls(Shell shell){
		createToolItem(shell);
	}
	
	private void createToolItem(Shell shell) {
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
	    
	    ToolItem itemPush = new ToolItem(toolBar, SWT.PUSH);
	    itemPush.setText("PUSH text");
	    itemPush.setToolTipText(TOOLTIP);
	    
	    ToolItem itemPush2 = new ToolItem(toolBar, SWT.PUSH);
	    itemPush2.setText("PUSH text2");
	    itemPush2.setToolTipText(TOOLTIP+"2");
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
	public void testToolItemTooltip(){
		DefaultToolItem ti = new DefaultToolItem(TOOLTIP);
		assertTrue("Item has \"PUSH tooltip\" as a tooltip",ti.getToolTipText().equals(TOOLTIP));
		assertFalse("Item has not \"" + TOOLTIP + "2\" as a tooltip",ti.getToolTipText().equals(TOOLTIP + "2"));
	}
}
