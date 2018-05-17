/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.execution.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.execution.TestMethodShouldRun;


/**
 * Annotation for marking a junit test method as conditional.  
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
	 * @return condition class to check whether a test method should run
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
