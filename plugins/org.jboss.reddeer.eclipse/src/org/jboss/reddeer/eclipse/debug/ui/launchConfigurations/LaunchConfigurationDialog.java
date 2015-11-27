package org.jboss.reddeer.eclipse.debug.ui.launchConfigurations;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.condition.ProgressInformationShellIsActive;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.CLabelWithTextIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.YesButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Common ancestor for both the Run Configurations dialog and Debug configurations dialog. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class LaunchConfigurationDialog {

	private static final Logger log = Logger.getLogger(LaunchConfigurationDialog.class);

	/**
	 * Return the title of the dialog. 
	 * 
	 * @return Title of the dialog
	 */
	public abstract String getTitle();

	/**
	 * Gets the menu item name.
	 *
	 * @return the menu item name
	 */
	protected abstract String getMenuItemName();

	/**
	 * Open the dialog using top menu.
	 */
	public void open() {
		log.info("Open launch configuration dialog");

		Menu menu = new ShellMenu("Run", getMenuItemName());
		menu.select();

		new DefaultShell(getTitle());
	}

	/**
	 * Select the launch configuration.
	 *
	 * @param configuration the configuration
	 */
	public void select(LaunchConfiguration configuration) {
		log.info("Select launch configuration " + configuration.getType());
		TreeItem t = new DefaultTreeItem(configuration.getType());
		t.select();
	}
	
	/**
	 * Select the launch configuration with the specified name.
	 *
	 * @param configuration the configuration
	 * @param name the name
	 */
	public void select(LaunchConfiguration configuration, String name) {
		log.info("Select launch configuration " + configuration.getType() + " with name " + name);
		TreeItem t = new DefaultTreeItem(configuration.getType(), name);
		t.select();

		new WaitUntil(new CLabelWithTextIsAvailable(name), TimePeriod.NORMAL, false);
	}

	/**
	 * Create new configuration with default name.
	 *
	 * @param configuration the configuration
	 */
	public void create(LaunchConfiguration configuration){
		log.info("Create new launch configuration " + configuration.getType());
		create(configuration, null);
	}

	/**
	 * Create new configuration with specified name.
	 *
	 * @param configuration the configuration
	 * @param name the name
	 */
	public void create(LaunchConfiguration configuration, String name){
		log.info("Create new launch configuration " + configuration.getType() + " with name " + name);
		TreeItem t = new DefaultTreeItem(configuration.getType());
		t.select();
		
		new WaitWhile(new ProgressInformationShellIsActive());
		t.select();

		log.info("Wait until");
		new WaitUntil(new JobIsRunning(), TimePeriod.SHORT, false);
		log.info("Wait while");
		new WaitWhile(new JobIsRunning(), TimePeriod.NORMAL);
		
		log.info("Active shells:");
		for (Shell shell : ShellLookup.getInstance().getShells()){
			log.info("\t" + WidgetHandler.getInstance().getText(shell));
		}
		
		log.info("Default shell: " + new DefaultShell(getTitle()));
		log.info("Focused control: " + WidgetLookup.getInstance().getFocusControl());
		Control c = WidgetLookup.getInstance().getFocusControl();
		if (c instanceof Shell){
			log.info("Focused control is shell: " + WidgetHandler.getInstance().getText(c));
		}
		
		log.info("Select tree item again");
		t.select();
		
		log.info("Focused control: " + WidgetLookup.getInstance().getFocusControl());
		c = WidgetLookup.getInstance().getFocusControl();
		if (c instanceof Shell){
			log.info("Focused control is shell: " + WidgetHandler.getInstance().getText(c));
		}
		try {
			new ContextMenu("New").select();
		} catch (RedDeerException e){
			log.error("Error: " + new DefaultShell().getText());
			new WaitWhile(new JobIsRunning(), TimePeriod.NONE);
			throw e;
		}
		if (name != null){
			configuration.setName(name);
			configuration.apply();
		}
	}
	
	/**
	 * Delete the configuration with specified name.
	 *
	 * @param configuration the configuration
	 * @param name the name
	 */
	public void delete(LaunchConfiguration configuration, String name){
		log.info("Delete launch configuration " + configuration.getType() + " with name " + name);
		TreeItem t = new DefaultTreeItem(configuration.getType(), name);
		t.select();

		new ContextMenu("Delete").select();
		new YesButton().click();
	}
	
	/**
	 * Run the selected run configuration.
	 */
	public void run(){
		log.info("Run the launch configuration");

		String shellText = new DefaultShell().getText();
		Button button = new PushButton("Run");
		button.click();

		new WaitWhile(new ShellWithTextIsActive(shellText), TimePeriod.VERY_LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);			
	}

	/**
	 * Close the dialog.
	 */
	public void close(){
		log.info("Close the launch configuration dialog");

		String shellText = new DefaultShell().getText();
		Button button = new PushButton("Close");
		button.click();

		new WaitWhile(new ShellWithTextIsActive(shellText));
		new WaitWhile(new JobIsRunning());
	}
}
