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
	 * Check if the requirement can be fullfiled
	 * @return true if the requirement can be fullfiled, false otherwise
	 */
	boolean canFulfill();

	/**
	 * Fullfill the requirement
	 */
	void fulfill();
	
	void setDeclaration(T declaration);
}
