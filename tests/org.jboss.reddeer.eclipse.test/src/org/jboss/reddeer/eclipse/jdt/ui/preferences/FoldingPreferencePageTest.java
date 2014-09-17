package org.jboss.reddeer.eclipse.jdt.ui.preferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class FoldingPreferencePageTest {

	private static FoldingPreferencePage page = new FoldingPreferencePage();

	private WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
	
	@Before
	public void setUp() {
		dialog.open();
		dialog.select(page);
	}

	@After
	public void tearDown() {
		dialog.cancel();
	}

	@Test
	public void testFolding() {
		page.disableFolding();
		assertFalse(page.isFoldingChecked());
		page.enableFolding();
		assertTrue(page.isFoldingChecked());
	}

	@Test
	public void testComments() {
		page.enableComments();
		assertTrue(page.isCommentsChecked());
		page.disableComments();
		assertFalse(page.isCommentsChecked());

	}

	@Test
	public void testHeaderComments() {
		page.disableHeaderComments();
		assertFalse(page.isHeaderCommentsChecked());
		page.enableHeaderComments();
		assertTrue(page.isHeaderCommentsChecked());
	}

	@Test
	public void testInnerTypes() {
		page.enableInnerTypes();
		assertTrue(page.isInnerTypesChecked());
		page.disableInnerTypes();
		assertFalse(page.isInnerTypesChecked());
	}

	@Test
	public void testMembers() {
		page.enableMembers();
		assertTrue(page.isMembersChecked());
		page.disableMembers();
		assertFalse(page.isMembersChecked());
	}

	@Test
	public void testImports() {
		page.enableImports();
		assertTrue(page.isImportsChecked());
		page.disableImports();
		assertFalse(page.isImportsChecked());
	}

}

