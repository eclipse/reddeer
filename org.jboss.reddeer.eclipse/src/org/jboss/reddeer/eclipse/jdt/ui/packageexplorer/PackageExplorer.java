package org.jboss.reddeer.eclipse.jdt.ui.packageexplorer;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.jboss.reddeer.swt.condition.JobsAreNotActive;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.util.Jobs;
import org.jboss.reddeer.swt.wait.WaitUntilCondition;
import org.jboss.reddeer.swt.wait.WaitWhileCondition;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;

/**
 * Represents Package Explorer in Eclipse
 * 
 * @author vpakan
 *
 */
public class PackageExplorer extends WorkbenchView {
    
	public PackageExplorer() {
		super("Package Explorer");
	}
	
	public void selectItem (String... path){
	  open();
	  new DefaultTreeItem(path).select();
	}
	
	public boolean contains (String... path){
	  boolean result = false;
	  try{
	    selectItem(path);
	    result = true;
	  } catch (WidgetNotFoundException wnfe){
	    result = false;
	  }
	  return result;
	}
	
	public void deleteItem (String path, boolean deleteFromFileSystem){
	  selectItem(path);
	  log.debug("Delete item via Package Explorer");
	  new ContextMenu("Delete").select();
	  new DefaultShell("Delete Resources");
	  SWTBotCheckBox chbDeleteFromFileSystem = Bot.get().checkBox();
	  if ((chbDeleteFromFileSystem.isChecked() && !deleteFromFileSystem) ||
	      (!chbDeleteFromFileSystem.isChecked() && deleteFromFileSystem)){
	    chbDeleteFromFileSystem.click();
	  }
	  DefaultShell shell = new DefaultShell();
	  new PushButton("OK").click();
	  new WaitWhileCondition(new ShellWithTextIsActive(shell.getText()));
	  new WaitUntilCondition(new JobsAreNotActive(Jobs.BUILDING_WORKSPACE_JOB,
		  Jobs.COMPACTING_RESOURCE_MODEL,
		  Jobs.LOADING_JOB),
		  30000);
	}
}
