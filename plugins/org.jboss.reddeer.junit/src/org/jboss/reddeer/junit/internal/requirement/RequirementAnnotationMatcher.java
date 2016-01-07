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
package org.jboss.reddeer.junit.internal.requirement;

import java.lang.annotation.Annotation;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Checks if the annotation is a requirement annotation.  The annotation is a requirement annotation if it is enclosed by a class that implements {@link Requirement} interface.
 * 
 * @author Lucia Jelinkova
 *
 */
public class RequirementAnnotationMatcher extends TypeSafeMatcher<Annotation> {

	/* (non-Javadoc)
	 * @see org.hamcrest.TypeSafeMatcher#matchesSafely(java.lang.Object)
	 */
	@Override
	public boolean matchesSafely(Annotation annotation) {
		Class<?> enclosingType = annotation.annotationType().getEnclosingClass();
		
		if (enclosingType == null){
			return false;
		}
		
		return Requirement.class.isAssignableFrom(enclosingType);
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description description) {
		
	}
}
