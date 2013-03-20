package org.jboss.reddeer.swt.locate;

/**
 * Classes implementing this interface can be used
 * as composite widget parent from which children are
 * located
 */
public interface CompositeWidget {

	/**
	 * Update the reference of composite widget parent 
	 */
	void setCompositeWidget();
	
}
