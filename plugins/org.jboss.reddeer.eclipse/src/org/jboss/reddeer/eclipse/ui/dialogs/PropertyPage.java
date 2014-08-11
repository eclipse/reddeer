package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Represents a general property page independent of the resource type.
 * 
 * @author Lucia Jelinkova
 * 
 */
public abstract class PropertyPage extends PreferencePage {

	protected String[] path;
	
	public PropertyPage(String... path) {
		super();
		this.path = path;
	}
	
	protected abstract String getResourceName();
	
	/**
	 * Opens "Properties" for the selected resource and selects the right property page from the Properties dialog. 
	 * <br />
	 * Note that it is the responsibility of subclasses to make sure that the right resource is selected.
	 * 
	 */
	@Override
	public void open() {
		new ContextMenu("Properties").select();;
		
		new WaitUntil(new ShellWithTextIsActive(getPageTitle()));
		
		TreeItem t = new DefaultTreeItem(path);
		t.select();
	}
	
	/**
	 * Returns page title.
	 * 
	 * @return Page title
	 */
	public String getPageTitle(){
		return "Properties for " + getResourceName();
	}
}
