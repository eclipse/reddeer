package org.jboss.reddeer.swt.api;

import java.util.List;

/**
 * API for Tree manipulation
 * @author Jiri Peterka
 *
 */
public interface Tree {

	/**
	 * @return Top level tree items. 
	 *  
	 */
	List<TreeItem> getItems();
			
	/** 
	 * @return All tree items recursively.
	 */
	List<TreeItem> getAllItems();
}
