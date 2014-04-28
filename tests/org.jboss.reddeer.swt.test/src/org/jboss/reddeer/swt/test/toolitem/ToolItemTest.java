package org.jboss.reddeer.swt.test.toolitem;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.junit.Test;

public class ToolItemTest extends SWTLayerTestCase{

	private final String TOOLTIP="PUSH tooltip";
	
	@Override
	protected void createControls(Shell shell){
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
	    
	    ToolItem itemPush = new ToolItem(toolBar, SWT.PUSH);
	    itemPush.setText("PUSH text");
	    itemPush.setToolTipText(TOOLTIP);
	    
	    ToolItem itemPush2 = new ToolItem(toolBar, SWT.PUSH);
	    itemPush2.setText("PUSH text2");
	    itemPush2.setToolTipText(TOOLTIP+"2");
	}

	@Test
	public void testToolItemTooltip(){
		DefaultToolItem ti = new DefaultToolItem(TOOLTIP);
		assertTrue("Item has \"PUSH tooltip\" as a tooltip",ti.getToolTipText().equals(TOOLTIP));
		assertFalse("Item has not \"" + TOOLTIP + "2\" as a tooltip",ti.getToolTipText().equals(TOOLTIP + "2"));
	}
}
