package org.jboss.reddeer.eclipse.datatools.ui.wizard;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.list.DefaultList;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tab.DefaultTabItem;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * A wizard page in a driver definition wizard.
 * 
 * @author apodhrad
 * 
 */
public class DriverDefinitionPage extends WizardPage {

	public static final String LABEL_DRIVER_CLASS = "Driver Class";
	public static final String LABEL_DRIVER_NAME = "Driver name:";
	public static final String TAB_NAME_TYPE = "Name/Type";
	public static final String TAB_PROPERTIES = "Properties";
	public static final String TAB_JAR_LIST = "JAR List";
	public static final String BUTTON_CLEAR_ALL = "Clear All";
	public static final String BUTTON_REMOVE_JAR = "Remove JAR/Zip";

	public DriverDefinitionPage() {
		super();
	}

	/**
	 * Set a driver name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		new LabeledText(LABEL_DRIVER_NAME).setText(name);
	}

	/**
	 * Select a driver template.
	 * 
	 * @param type
	 * @param vendor
	 * @param version
	 */
	public void selectDriverTemplate(String type, String version) {
		selectTab(TAB_NAME_TYPE);
		Tree tree = new DefaultTree();
		// Database
		TreeItem root = tree.getItems().get(0);
		for (TreeItem item : root.getItems()) {
			if (type.equals(item.getCell(0)) && version.equals(item.getCell(2))) {
				item.select();
				break;
			}
		}
	}

	/**
	 * Sets a given driver class. Not yet implemented!
	 * 
	 * @param driverClass
	 *            Driver class
	 */
	public void setDriverClass(String driverClass) {
		selectTab(TAB_PROPERTIES);
		new DefaultTreeItem("General","Driver Class").doubleClick();
		new PushButton("...").click();
		String dlgTitle = "Available Classes from Jar List";
		new WaitUntil(new ShellWithTextIsActive(dlgTitle));
		new DefaultShell(dlgTitle);
		new RadioButton(0).click();
		new DefaultText("").setText(driverClass);
		new OkButton().click();		
	}

	/**
	 * Add a driver library.
	 * 
	 * @param driverLocation
	 *            an absolute path to the driver lib
	 */
	public void addDriverLibrary(String driverLocation) {
		selectTab(TAB_JAR_LIST);
		clearAllDriverLibraries();
		addItem(driverLocation);
		addItem(driverLocation);
		removeDriverLibrary(driverLocation);
	}

	/**
	 * Remove a given library.
	 * 
	 * @param driverLocation
	 */
	public void removeDriverLibrary(String driverLocation) {
		new DefaultList().select(driverLocation);
		new PushButton(BUTTON_REMOVE_JAR).click();
	}

	/**
	 * Remove all listed libraries.
	 */
	public void clearAllDriverLibraries() {

		if (new DefaultList().getListItems().length > 0) {			
			new PushButton(BUTTON_CLEAR_ALL).click();
		} else {
			log.info("No drivers to clean, skipped");
		}			
	}

	/**
	 * Select a given tab
	 * 
	 * @param label
	 */
	public void selectTab(String label) {
		new DefaultTabItem(label).activate();
	}

	/**
	 * Directly add an item to the list
	 * 
	 * @param item
	 */
	private void addItem(String item) {
		new ExtendedDeafultList().addItem(item);
	}

	/**
	 * DefaultList extended by directly adding an item to the list
	 * 
	 * @author apodhrad
	 *
	 */
	private class ExtendedDeafultList extends DefaultList {

		public ExtendedDeafultList() {
			super();
		}

		public void addItem(final String item) {
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					list.add(item);
				}
			});
		}
	}

}
