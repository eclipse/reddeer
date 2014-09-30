package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.test.jdt.ui.AbstractExplorerItemTest;
import org.junit.Test;

public class PackageExplorerItemTest extends AbstractExplorerItemTest {

	public PackageExplorerItemTest() {
		super(new PackageExplorer());
	}
	
	@Test
	public void open() {
		open(PROJECT_ITEM_TEXT,DEFAULT_PACKAGE_TEXT, JAVA_CLASS_FILE_NAME);
	}
	
	@Test
	public void getChild() {
		getChild(PROJECT_ITEM_TEXT, DEFAULT_PACKAGE_TEXT);
	}
	
	@Test
	public void getChildren() {
		getChildren(PROJECT_ITEM_TEXT, DEFAULT_PACKAGE_TEXT);
	}
}
