package org.jboss.reddeer.requirements.server;

/**
 * 
 * @author Pavol Srna
 *
 */
public interface IServerFamily {
	
	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory();
	
	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel();
	
	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion();
}
