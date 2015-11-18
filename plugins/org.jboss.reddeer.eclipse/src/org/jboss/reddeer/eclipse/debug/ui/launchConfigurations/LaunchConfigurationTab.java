package org.jboss.reddeer.eclipse.debug.ui.launchConfigurations;

import org.jboss.reddeer.swt.impl.ctab.DefaultCTabItem;

/**
 * Represents one tab in Launch configuration. One tab can be used in 
 * different configuratins. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class LaunchConfigurationTab {
	
	private String name;
	
	/**
	 * Construct launch configuration tab with the given name.
	 *
	 * @param name Tab name
	 */
	public LaunchConfigurationTab(String name) {
		this.name = name;
	}
	
	/**
	 * Activates the tab.
	 */
	public void activate(){
		new DefaultCTabItem(name).activate();
	}
}
