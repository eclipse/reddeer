package org.jboss.reddeer.swt.reference;

/**
 * Classes implementing this interface can be used
 * as composite widget parent from which children are
 * located
 * @author Jaroslav Jankovic
 * @author Jiri Peterka
 */
public interface ReferencedComposite {

	/**
	 * Update the reference of composite widget parent 
	 */
	void setAsReference();
	
}
