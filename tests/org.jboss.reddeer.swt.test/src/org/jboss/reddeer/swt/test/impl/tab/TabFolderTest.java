package org.jboss.reddeer.swt.test.impl.tab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.tab.DefaultTabFolder;
import org.jboss.reddeer.swt.impl.tab.DefaultTabItem;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.junit.Test;

/**
 * 
 * @author Andrej Podhradsky
 * @author Vlado Pakan
 * 
 */
public class TabFolderTest extends SWTLayerTestCase {

	private static final String ITEM_LABEL_PREFIX = "Item ";
	private static final String TOOLTIP_PREFIX = "Tool for Item ";
	private static final String CONTENT_PREFIX = "Content for Item ";

	@Override
	protected void createControls(Shell shell) {
		org.eclipse.swt.widgets.TabFolder folder = new org.eclipse.swt.widgets.TabFolder(shell,
				SWT.BORDER);
		for (int i = 0; i < 4; i++) {
			org.eclipse.swt.widgets.TabItem item = new org.eclipse.swt.widgets.TabItem(folder,
					SWT.NONE);
			item.setText(TabFolderTest.ITEM_LABEL_PREFIX + i);
			item.setToolTipText(TabFolderTest.TOOLTIP_PREFIX + i);
			Text text = new Text(folder, SWT.MULTI);
			text.setText(TabFolderTest.CONTENT_PREFIX + i);
			item.setControl(text);
		}
	}

	@Test
	public void findByIndexAndActivate() {
		int index = 2;
		new DefaultTabItem(index).activate();
		String expectedTabItemContent = TabFolderTest.CONTENT_PREFIX + index;
		String tabItemContent = new DefaultText(0).getText();
		assertTrue("cTabItem content is " + tabItemContent + "\nbut expected CTabItem content is "
				+ expectedTabItemContent, tabItemContent.equals(expectedTabItemContent));
	}

	@Test
	public void findByNameAndActivate() {
		int index = 1;
		new DefaultTabItem(TabFolderTest.ITEM_LABEL_PREFIX + index).activate();
		String expectedTabItemContent = TabFolderTest.CONTENT_PREFIX + index;
		String tabItemContent = new DefaultText(0).getText();
		assertTrue("cTabItem content is " + tabItemContent + "\nbut expected CTabItem content is "
				+ expectedTabItemContent, tabItemContent.equals(expectedTabItemContent));
	}

	@Test(expected = SWTLayerException.class)
	public void findNonExistingByIndex() {
		new DefaultTabItem(5);
	}

	@Test(expected = SWTLayerException.class)
	public void findNonExistingByLabel() {
		new DefaultTabItem("NON_EXISTING_#$");
	}
	
	@Test
	public void tabFolderTest() {
		String[] tabItemLabel = new DefaultTabFolder().getTabItemLabels();
		assertEquals(4, tabItemLabel.length);
		assertEquals("Item 0", tabItemLabel[0]);
		assertEquals("Item 1", tabItemLabel[1]);
		assertEquals("Item 2", tabItemLabel[2]);
		assertEquals("Item 3", tabItemLabel[3]);
	}

}
