package org.jboss.reddeer.swt.test.impl.ctab;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.swt.api.CTabItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.ctab.DefaultCTabItem;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.junit.After;
import org.junit.Test;

public class CTabFolderTest extends RedDeerTest{
	private static final String ITEM_LABEL_PREFIX = "Item ";
	private static final String TOOLTIP_PREFIX = "Tool for Item ";
	private static final String CONTENT_PREFIX = "Content for Item ";
	@Override
	public void setUp() {
		super.setUp();
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = new Shell(org.eclipse.swt.widgets.Display.getDefault());  
				shell.setText("Testing shell");
				createControls(shell);
				shell.open();
				shell.setFocus();
			}
		});
	}
	
	private void createControls(Shell shell){
		shell.setLayout(new FillLayout());
		CTabFolder folder = new CTabFolder(shell, SWT.BORDER);
		for (int i = 0; i < 4; i++) {
			org.eclipse.swt.custom.CTabItem item = new org.eclipse.swt.custom.CTabItem(folder, SWT.CLOSE);
			item.setText(CTabFolderTest.ITEM_LABEL_PREFIX + i);
			item.setToolTipText(CTabFolderTest.TOOLTIP_PREFIX + i);
			Text text = new Text(folder, SWT.MULTI);
			text.setText(CTabFolderTest.CONTENT_PREFIX +i);
			item.setControl(text);
		}
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
	public void findByIndexAndActivate(){
		int index = 2;
		new DefaultCTabItem(index).activate();
		String expectedCTabItemContent = CTabFolderTest.CONTENT_PREFIX + index;
		String cTabItemContent = new DefaultText(0).getText(); 
		assertTrue("cTabItem content is " + cTabItemContent
				+ "\nbut expected CTabItem content is " + expectedCTabItemContent,
			cTabItemContent.equals(expectedCTabItemContent));
	}
	@Test
	public void findByNameAndActivate(){
		int index = 1;
		new DefaultCTabItem(CTabFolderTest.ITEM_LABEL_PREFIX + index).activate();
		String expectedCTabItemContent = CTabFolderTest.CONTENT_PREFIX + index;
		String cTabItemContent = new DefaultText(0).getText(); 
		assertTrue("cTabItem content is " + cTabItemContent
				+ "\nbut expected CTabItem content is " + expectedCTabItemContent,
			cTabItemContent.equals(expectedCTabItemContent));
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingByIndex(){
		new DefaultCTabItem(5);
	}
	@Test(expected = SWTLayerException.class)
	public void findNonExistingByLabel(){
		new DefaultCTabItem("NON_EXISTING_#$");
	}
	@Test
	public void close(){
		int index = 3;
		CTabItem cTabItem = new DefaultCTabItem(index);
		cTabItem.close();
		try{
			new DefaultCTabItem(index);
			fail("CTabItem with index " + index + " was found but has to be closed");
		}
		catch (SWTLayerException sle){
			// do nothing closed CTabItem should not be found
		}
	}
}
