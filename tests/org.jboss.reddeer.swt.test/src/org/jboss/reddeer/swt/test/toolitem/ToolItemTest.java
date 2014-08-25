package org.jboss.reddeer.swt.test.toolitem;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.matcher.RegexMatcher;
import org.jboss.reddeer.swt.matcher.WithTooltipTextMatcher;
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
		itemPush2.setToolTipText(TOOLTIP + "2");
	    
		ToolBar toolBar2 = new ToolBar(shell, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

		ToolItem itemPush1TB2 = new ToolItem(toolBar2, SWT.PUSH);
		itemPush1TB2.setText("PUSH text ToolBar2");
		itemPush1TB2.setToolTipText(TOOLTIP + " tb2");

		ToolItem itemPush2TB2 = new ToolItem(toolBar2, SWT.PUSH);
		itemPush2TB2.setText("PUSH text 2 ToolBar2");
		itemPush2TB2.setToolTipText(TOOLTIP + "2 tb2");
	}

	@Test
	public void testToolItemTooltip() {
		DefaultToolItem ti = new DefaultToolItem(TOOLTIP);
		assertTrue("Item has \"PUSH tooltip\" as a tooltip", ti
				.getToolTipText().equals(TOOLTIP));
		assertFalse("Item has not \"" + TOOLTIP + "2\" as a tooltip", ti
				.getToolTipText().equals(TOOLTIP + "2"));
	}

	@Test
	public void constructorWithIndex() {
		DefaultToolItem ti = new DefaultToolItem(2);
		assertTrue("Item has \"PUSH tooltip\" as a tooltip", ti
				.getToolTipText().equals(TOOLTIP));
		assertFalse("Item has not \"" + TOOLTIP + "2\" as a tooltip", ti
				.getToolTipText().equals(TOOLTIP + "2"));
	}

	@Test
	public void constructorWithRegexTest() {
		DefaultToolItem ti = new DefaultToolItem(new WithTooltipTextMatcher(
				new RegexMatcher("PUSH.*tb2")));
		assertTrue("Item has \"PUSH tooltip tb2\" as a tooltip", ti
				.getToolTipText().equals(TOOLTIP + " tb2"));
	}

	@Test
	public void constructorWithRegexWithReferencedComposite(){
		DefaultToolItem ti = new DefaultToolItem();
		System.out.println(ti.getToolTipText());
	}
	
	@Test
	public void WorkbenchToolItemWithMnemonicTest() {
		new DefaultToolItem(new WorkbenchShell(), new WithTooltipTextMatcher(
				"RedDeer SWT WorkbenchToolItem with mnemonic"));
	}
}
