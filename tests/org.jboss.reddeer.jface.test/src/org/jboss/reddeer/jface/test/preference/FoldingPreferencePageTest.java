package org.jboss.reddeer.jface.test.preference;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jface.preference.FoldingPreferencePage;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.Test;

public class FoldingPreferencePageTest extends RedDeerTest {

	private static FoldingPreferencePage page = new FoldingPreferencePage();

	@Override
	protected void setUp() {
		super.setUp();
		page.open();
	}

	@Override
	protected void tearDown() {
		page.cancel();
		super.tearDown();
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
