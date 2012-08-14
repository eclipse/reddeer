package org.jboss.reddeer.eclipse.jdt.ui.packageexplorer;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.workbench.view.WorkbenchView;

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
	  new ContextMenu("Delete").select();
	  new ActiveShell("Delete Resources");
	  SWTBotCheckBox chbDeleteFromFileSystem = Bot.get().checkBox();
	  if ((chbDeleteFromFileSystem.isChecked() && !deleteFromFileSystem) ||
	      (!chbDeleteFromFileSystem.isChecked() && deleteFromFileSystem)){
	    chbDeleteFromFileSystem.click();
	  }
	  new PushButton("OK").click();
	}
}
