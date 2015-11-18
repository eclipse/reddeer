package org.jboss.reddeer.junit.requirement;

import java.lang.annotation.Annotation;

/**
 * 
 * Represents one requirement that should be fulfilled before the test runs. 
 * If it cannot be fulfilled the test should be ignored.
 * 
 * @author Lucia Jelinkova
 *
 */
public interface Requirement<T extends Annotation> {
	
	/**
	 * Can fulfill.
	 *
	 * @return true, if successful
	 */
	boolean canFulfill();

	/**
	 * Fulfill.
	 */
	void fulfill();
	
	/**
	 * Sets the declaration.
	 *
	 * @param declaration the new declaration
	 */
	void setDeclaration(T declaration);
	
	/**
	 * Clean up after requirement.
	 */
	void cleanUp();
}
