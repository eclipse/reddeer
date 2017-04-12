/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.eclipse.m2e.core.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Class representing "Maven" &gt; "Archetypes" preference page.
 * 
 * @author jkopriva
 *
 */

public class MavenArchetypesPreferencePage extends PreferencePage {

	private static final String ADD_LOCAL_CATALOG = "Add Local Catalog...";
	private static final String ADD_REMOTE_CATALOG = "Add Remote Catalog...";
	private static final String EDIT_CATALOG = "Edit...";
	private static final String REMOVE_CATALOG = "Remove";
	private static final String LOCAL_CATALOG_SHELL = "Local Archetype Catalog";
	private static final String REMOTE_CATALOG_SHELL = "Remote Archetype Catalog";
	private static final String CATALOG_DESCRIPTION = "Description:";
	private static final String VERIFY_BUTTON = "Verify...";

	private static final Logger log = Logger.getLogger(MavenArchetypesPreferencePage.class);

	/**
	 * Construct the preference page with "Maven" &gt; "Archetypes".
	 */
	public MavenArchetypesPreferencePage() {
		super(new String[] { "Maven", "Archetypes" });
	}

	/**
	 * Add new archetypes local catalog to Eclipse.
	 * 
	 * @param catalogFile
	 *            path to the XML catalog file, e.g. archetype-catalog.xml
	 * @param description
	 *            description of the local catalog
	 */
	public void addLocalCatalog(String catalogFile, String description) {
		new PushButton(ADD_LOCAL_CATALOG).click();
		new WaitUntil(new ShellIsAvailable(LOCAL_CATALOG_SHELL), TimePeriod.DEFAULT);
		new DefaultCombo().setText(catalogFile);
		new LabeledText(CATALOG_DESCRIPTION).setText(description);
		new OkButton().click();
	}

	/**
	 * Add new archetypes remote catalog to Eclipse. Catalog is verified.
	 * Returns status of verification.
	 * 
	 * @param catalogFileURL
	 *            URL to the remote XML catalog file, e.g. archetype-catalog.xml
	 * @param description
	 *            description of the local catalog
	 * @return verificationResult status of verification
	 */
	public String addRemoteCatalog(String catalogFileURL, String description) {
		return addRemoteCatalog(catalogFileURL, description, true);
	}

	/**
	 * Add new archetypes remote catalog to Eclipse. Catalog is verified depends
	 * on verify parameter. Returns status of verification(if parameter verify
	 * is true, otherwise it returns empty string).
	 * 
	 * @param catalogFileURL
	 *            URL to the remote XML catalog file, e.g. archetype-catalog.xml
	 * @param description
	 *            description of the local catalog
	 * @param verify whether a catalog URL should be verified
	 * @return verificationResult status of verification
	 */
	public String addRemoteCatalog(String catalogFileURL, String description, boolean verify) {
		String verificationResult = "";
		new PushButton(ADD_REMOTE_CATALOG).click();
		new WaitUntil(new ShellIsAvailable(REMOTE_CATALOG_SHELL), TimePeriod.DEFAULT);
		new DefaultCombo().setText(catalogFileURL);
		new LabeledText(CATALOG_DESCRIPTION).setText(description);
		if (verify) {
			verificationResult = verifyURL();
		}
		log.info(verificationResult);
		new OkButton().click();
		return verificationResult;
	}

	/**
	 * Returns list of catalog TableItems available in Maven Archetypes
	 * Preference page.
	 * 
	 * @return list of TableItems with catalogs from table
	 */
	public List<TableItem> getCatalogs() {
		Table table = new DefaultTable();
		return table.getItems();
	}

	/**
	 * Returns list of all catalog names available in Maven Archetypes
	 * Preference page.
	 * 
	 * @return list of catalog names from table
	 */
	public List<String> getCatalogNames() {
		List<String> catalogNames = new ArrayList<String>();
		for (TableItem item : getCatalogs()) {
			catalogNames.add(item.getText());
		}
		return catalogNames;
	}

	/**
	 * Removes Archetypes catalog from Eclipse.
	 * 
	 * @param catalogName
	 *            name of the catalog to be deleted
	 */
	public void removeCatalog(String catalogName) {
		selectCatalog(catalogName);
		new PushButton(REMOVE_CATALOG).click();
	}

	/**
	 * Edit local catalog.
	 * 
	 * @param catalogName
	 *            name of the catalog
	 * @param catalogFile
	 *            name of the catalog file
	 * @param description
	 *            description of the edited catalog
	 */
	public void editLocalCatalog(String catalogName, String catalogFile, String description) {
		editCatalog(catalogName, catalogFile, description, false);
	}

	/**
	 * Edit remote catalog and verify. Returns status of verification.
	 * 
	 * @param catalogName
	 *            URL of the catalog file
	 * @param catalogFile
	 *            name of the catalog file
	 * @param description
	 *            description of the edited catalog
	 * @return verificationResult status of verification
	 */
	public String editRemoteCatalog(String catalogName, String catalogFile, String description) {
		return editCatalog(catalogName, catalogFile, description, true);
	}

	/**
	 * Edit remote catalog. Returns status of verification(if parameter verify
	 * is true, otherwise it returns empty string).
	 * 
	 * @param catalogName
	 *            URL of the catalog file
	 * @param catalogFile
	 *            name of the catalog file
	 * @param description
	 *            description of the edited catalog
	 * @param verify
	 *            true - verify remote catalog, false - do not verify remote
	 *            catalog
	 * @return verificationResult status of verification
	 */
	public String editRemoteCatalog(String catalogName, String catalogFile, String description, boolean verify) {
		return editCatalog(catalogName, catalogFile, description, verify);
	}

	/**
	 * Private method for editing catalog by name. Returns status of
	 * verification(if parameter verify is true, otherwise it returns empty
	 * string).
	 * 
	 * @param catalogName
	 *            URL of the catalog file
	 * @param catalogFile
	 *            name of the catalog file
	 * @param description
	 *            description of the edited catalog
	 * @param verify
	 *            true - verify remote catalog, false - do not verify remote
	 *            catalog (only for remote catalogs)
	 * @return verificationResult status of verification
	 */
	private String editCatalog(String catalogName, String catalogFile, String description, boolean verify) {
		String verificationResult = "";
		selectCatalog(catalogName);
		new PushButton(EDIT_CATALOG).click();
		new DefaultCombo().setText(catalogFile);
		new LabeledText(CATALOG_DESCRIPTION).setText(description);
		if (verify) {
			verificationResult = verifyURL();
		}
		log.info(verificationResult);
		new OkButton().click();
		return verificationResult;
	}

	/**
	 * If catalog with given name is available, returns true, otherwise false.
	 * 
	 * @param catalogName catalog name
	 * @return true if catalog with given name exists, false otherwise
	 */
	public boolean containsCatalog(String catalogName) {
		for (String name : getCatalogNames()) {
			if (name.equals(catalogName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method for selecting catalogs.
	 * 
	 * @param catalogName
	 *            name of the catalog file
	 */
	public void selectCatalog(String catalogName) {
		for (TableItem item : getCatalogs()) {
			if (item.getText().equals(catalogName)) {
				item.select();
			}
		}
	}

	/**
	 * Protected method for verifying remote catalog.
	 * 
	 * @return result of verification
	 */
	protected String verifyURL() {
		new PushButton(VERIFY_BUTTON).click();
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		Text labeledText = new LabeledText(REMOTE_CATALOG_SHELL);
		String text = labeledText.getText();
		return text;
	}

}
