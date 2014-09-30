package org.jboss.reddeer.eclipse.test.ui.views;

import org.jboss.reddeer.eclipse.test.jdt.ui.AbstractExplorerItemTest;
import org.jboss.reddeer.eclipse.ui.views.navigator.ResourceNavigator;
import org.junit.Test;

public class ResourceNavigatorItemTest extends AbstractExplorerItemTest{

	public ResourceNavigatorItemTest() {
		super(new ResourceNavigator());
	}
	
	@Test
	public void open() {
		open(PROJECT_ITEM_TEXT,	JAVA_CLASS_FILE_NAME);
	}
	
	@Test
	public void getChild() {
		getChild(PROJECT_ITEM_TEXT);
	}
	
	@Test
	public void getChildren() {
		getChildren(PROJECT_ITEM_TEXT);
	}
}
