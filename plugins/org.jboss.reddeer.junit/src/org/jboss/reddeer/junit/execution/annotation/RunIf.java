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
package org.jboss.reddeer.junit.execution.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.execution.TestMethodShouldRun;


/**
 * Annotation for marking a junit test method as conditional. Please see {@link TestMethodShouldRun}  
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunIf {
	
	public enum Operation {
        AND,
        OR;
    }
	
	/**
	 * Condition class.
	 *
	 * @return the class<? extends test method should run>
	 */
	Class<? extends TestMethodShouldRun>[] conditionClass();
	
	/**
     * In case of several condition classes, if AND operation is selected then all conditions should pass,
     * if OR operation is selected then at least one condition should pass.
     * Defaults to AND
     *  
     * @return either Operation.AND or Operation.OR, defaults to Operation.AND
     */
    Operation operation() default Operation.AND;
}
