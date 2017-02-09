/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.requirements.openperspective;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.ui.perspectives.AbstractPerspective;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.requirements.exception.RequirementsLayerException;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;

/**
 * Open perspective requirement<br><br>
 * 
 * This {@link Requirement} ensures, that given perspective is active before actual test
 * execution.<br><br>
 * 
 * Annotate test class with {@link OpenPerspective} annotation to have
 * the given perspective opened before the test cases are executed.<br><br>
 * 
 * Example:<br>
 * <pre>
 * {@code @OpenPerspective(DebugPerspective.class)
 * public class TestClass {
 *    // debug perspective will be opened before tests execution
 * }
 * }
 * </pre>
 * @author rhopp
 * 
 */
public class OpenPerspectiveRequirement implements Requirement<OpenPerspective> {

	/**
	 * Marks test class, which requires opening of the specified perspective.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Documented
	public @interface OpenPerspective {
		
		/**
		 * specified perspective.
		 *
		 * @return the class&lt;? extends abstract perspective&gt;
		 */
		Class<? extends AbstractPerspective> value();
		
		/**
		 * If true, perspective will be reset to default. 
		 *
		 * @return true if successful, false otherwise
		 */
		boolean reset() default true;
	}

	private OpenPerspective openPerspective;

	/**
	 * Tests if a new instance of the given perspective can be created.
	 * 
	 * @return true if the given perspective is valid, otherwise false
	 */
	@Override
	public boolean canFulfill() {
		try {
			getPerspectiveInstance();
		} catch (InstantiationException ex) {
			throw new RequirementsLayerException(
					"There was problem initializing perspective", ex);
		} catch (IllegalAccessException e) {
			throw new RequirementsLayerException(
					"There was problem initializing perspective", e);
		} catch (EclipseLayerException ex) {
			return false; // this exception means, that perspective couldn't be
							// found
		}
		return true;
	}

	/**
	 * Opens the given perspective.
	 * 
	 * @throws RequirementsLayerException when the given perspective can't be opened
	 */
	@Override
	public void fulfill() {
		try {
			AbstractPerspective perspective = getPerspectiveInstance();
			perspective.open();
			
			if (openPerspective.reset()){
				perspective.reset();				
			}
		} catch (Exception e) {
			throw new RequirementsLayerException(
					"Unable to fullffill requirement 'Open Perspective'", e);
		}
	}

	/**
	 * Sets perspective, which will be opened before actual test execution.
	 * 
	 * @param openPerspective annotation defining the perspective to be opened
	 */
	@Override
	public void setDeclaration(OpenPerspective openPerspective) {
		this.openPerspective = openPerspective;

	}

	private AbstractPerspective getPerspectiveInstance()
			throws InstantiationException, IllegalAccessException {
		AbstractPerspective perspectiveInstance = null;
		perspectiveInstance = openPerspective.value().newInstance();
		return perspectiveInstance;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {

	}

}
