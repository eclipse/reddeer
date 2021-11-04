package org.eclipse.reddeer.eclipse.test.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.integration.test.installation.common.preferences.AvailableSoftwareSitesPreferencePage;
import org.eclipse.reddeer.jface.preference.PreferenceDialog;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class AvailableSoftwareSitesPreferencePageTest {
	
	private PreferenceDialog dialog;
	private AvailableSoftwareSitesPreferencePage page;
	private DefaultShell shell = null;
	private final static String ECLIPSE = "Eclipse";
	private final static String ECLIPSE_URL = "https://download.eclipse.org/releases/latest";
	private final static String REDDEER = "RedDeer";
	private final static String REDDEER_URL = "http://download.eclipse.org/reddeer/releases/latest";
	
	@BeforeClass
	public static void setupList() {
		Map<String, String> sites = new LinkedHashMap<String, String>();
		sites.put(ECLIPSE, ECLIPSE_URL);
		sites.put(REDDEER, REDDEER_URL);
		
		PreferenceDialog dialog = new WorkbenchPreferenceDialog();
		dialog.open();
		AvailableSoftwareSitesPreferencePage page = new AvailableSoftwareSitesPreferencePage(dialog);
		dialog.select(page);
		
		for(var site : sites.entrySet()) {
			page.clickAdd();
			LabeledText name = new LabeledText("Name:");
			name.setText(site.getKey());
			LabeledText url = new LabeledText("Location:");
			url.setText(site.getValue());
			new PushButton("Add").click();
		}
		
		dialog.ok();
		
	}
	
	@Before
	public void openAvailableSoftwareSitesPreferencePage() {
		dialog = new WorkbenchPreferenceDialog();
		dialog.open();
		page = new AvailableSoftwareSitesPreferencePage(dialog);
		dialog.select(page);
	}
	
	@Test
	public void pageIsOpened() {
		assertTrue("Page does not have right name", page.getName().contains("Available Software Sites"));
	}
	
	@Test
	public void getItemsTest() {
		assertFalse(page.getItems().isEmpty());
	}
	
	@Test
	public void getItemTest() {
		String itemText = page.getItem(ECLIPSE).getText();
		assertTrue("Item has text " + itemText, page.getItem(ECLIPSE).getText().contains(ECLIPSE));
	}
	
	@Test
	public void getItemAlternateSearchTest() {
		String itemText = page.getItem(REDDEER_URL, true).getText(1);
		assertTrue("the item has text " + itemText, itemText.contains(REDDEER_URL));
		itemText = page.getItem(ECLIPSE, false).getText();
		assertTrue("the item has text " + itemText, itemText.contains(ECLIPSE));
		TableItem item = page.getItem(ECLIPSE, true);
		assertNull("Item is not null", item);
		
	}
	
	@Test
	public void toggleAllItemsTest() {
		page.toggleAllItems(false);
		for(TableItem item : page.getItems()) {
			assertFalse(item.isChecked());
		}
		page.toggleAllItems(true);
		for(TableItem item : page.getItems()) {
			assertTrue(item.isChecked());
		}
	}
	
	@Test
	public void toggleItemTest() {
		page.toggleItem(ECLIPSE, false);
		assertFalse(page.getItem(ECLIPSE).isChecked());
		page.toggleItem(ECLIPSE, true);
		assertTrue(page.getItem(ECLIPSE).isChecked());
	}
	
	@Test
	public void toggleItemAlternativeSearchTest() {
		page.toggleItem(REDDEER_URL, false, true);
		assertFalse(page.getItem(REDDEER_URL, true).isChecked());
		page.toggleItem(REDDEER_URL, true, true);
		assertTrue(page.getItem(REDDEER).isChecked());
	}
	
	@Test
	public void selectItemTest() {
		page.selectItem(REDDEER);
		assertTrue(page.isButtonEnabled("Edit"));
	}
	
	@Test
	public void selectItemAlternativeSearchTest() {
		page.selectItem(ECLIPSE_URL, true);
		assertTrue(page.isButtonEnabled("Edit"));
	}
	
	@Test
	public void isItemEnabledTest() {
		assertTrue(page.isItemEnabled(ECLIPSE));
		page.toggleItem(ECLIPSE, false);
		assertFalse(page.isItemEnabled(ECLIPSE));
	}
	
	@Test
	public void isItemEnabledAlternativeSearchTest() {
		assertTrue(page.isItemEnabled(ECLIPSE_URL, true));
		page.toggleItem(ECLIPSE, false);
		assertFalse(page.isItemEnabled(ECLIPSE_URL, true));
	}

	@Test
	public void isButtonEnabledTest() {
		assertTrue(page.isButtonEnabled("Add..."));
		assertFalse(page.isButtonEnabled("Edit"));
	}
	
	@Test
	public void clickAddTest() {
		page.clickAdd();
		shell = new DefaultShell();
		assertTrue("Shell with title Add Site is not opened", shell.getText().contains("Add Site"));
	}
	
	@Test
	public void clickEditTest() {
		page.selectItem(ECLIPSE);
		page.clickEdit();
		shell = new DefaultShell();
		assertTrue("Shell with title Edit Site is not opened", shell.getText().contains("Edit Site"));
	}
	
	@Test
	public void clickRemoveTest() {
		page.selectItem(REDDEER);
		page.clickRemove();
		shell = new DefaultShell();
		assertTrue("Shell with title Remove Sites is not opened", shell.getText().contains("Remove Sites"));
	}
	
	@Test
	public void clickReloadTest() {
		page.selectItem(ECLIPSE);
		page.clickRemove();
		new WaitUntil(new ShellIsAvailable("Progress Information"));
		shell = new DefaultShell();
		assertTrue("Shell with title Reload is not opened", shell.getText().contains("Reload"));
	}
	
	@Test
	public void clickEnableTest() {
		page.toggleAllItems(false);
		page.selectItem(ECLIPSE);
		page.clickEnable();
		assertTrue("Item is not enabled",page.isItemEnabled(ECLIPSE));
	}
	
	@Test
	public void clickDisableTest() {
		page.selectItem(REDDEER);
		page.clickDisable();
		assertFalse("Item is not disabled", page.isItemEnabled(REDDEER));
	}
	
	@After
	public void closeAllShells() {
		if(shell != null) {
			shell.close();
		}
		dialog.cancel();
	}
}
