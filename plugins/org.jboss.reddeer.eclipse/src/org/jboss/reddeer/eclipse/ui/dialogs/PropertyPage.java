package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents a general property page.
 * 
 * @author Lucia Jelinkova
 * 
 */
public abstract class PropertyPage extends PreferencePage {

	public PropertyPage(String... path) {
		super(path);
	}
	
	/**
	 * @deprecated Use {@link ExplorerItemPropertyDialog}
	 * @return
	 */
	@Deprecated
	protected String getResourceName(){
		return null;
	}
	
	/**
	 * Opens "Properties" for the selected resource and selects the right property page from the Properties dialog. 
	 * <br />
	 * Note that it is the responsibility of subclasses to make sure that the right resource is selected.
	 * 
	 * @deprecated Use {@link ExplorerItemPropertyDialog}
	 * @return
	 */
	@Deprecated
	public void open() {
		new ContextMenu("Properties").select();;
		
		new WaitUntil(new ShellWithTextIsActive(getPageTitle()));
		
		TreeItem t = new DefaultTreeItem(getPath());
		t.select();
	}
	
	/**
	 * Returns page title.
	 * 
	 * @return Page title
	 * @deprecated Use {@link ExplorerItemPropertyDialog}
	 * @return
	 */
	@Deprecated
	public String getPageTitle(){
		return "Properties for " + getResourceName();
	}
}
