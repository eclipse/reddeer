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
package org.eclipse.reddeer.common.util;

import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.reddeer.common.exception.RedDeerException;

/**
 * Object util contains helper methods for method invocation using reflection, etc.
 * 
 * @author Jiri Peterka
 */
public class ObjectUtil {

	/**
	 * Invokes method using reflection. Widget based methods are executed in UI thread.
	 * 
	 * @param object object to invoke method on
	 * @param methodName method name to be invoked
	 * @return result of the method invocation
	 */
	public static Object invokeMethod(final Object object, String methodName) {
		return invokeMethod(object, methodName, new Class<?>[0], new Object[0]);
	}
	
	/**
	 * Invokes method using reflection. Widget based method are executed in UI thread.
	 *
	 * @param object object to invoke method on
	 * @param methodName method name to be invoked
	 * @param argTypes the arg types
	 * @param args the args
	 * @return result of the method invocation
	 */
	public static Object invokeMethod(final Object object, String methodName, final Class<?>[] argTypes, final Object[] args) {

		final Method method = getMethod(object, methodName, argTypes);
		
		final Object result;
		if (object instanceof Widget) {
			result = invokeMethodUI(method, object, args);
		} else {
			result = invokeMethod(method, object, args);
		}

		return result;
	}

	private static Method getMethod(final Object object, String methodName, final Class<?>[] argTypes) {
		final Method method;
		try {
			method = object.getClass().getMethod(methodName, argTypes);
		} catch (Exception e) {
			throw new RedDeerException("Exception when retrieving method " + methodName + " by reflection", e);
		}
		return method;
	}
	
	private static Object invokeMethodUI(final Method method, final Object object, final Object[] args) {
		return Display.syncExec(new ResultRunnable<Object>() {
			@Override
			public Object run() {
				return invokeMethod(method, object, args);
			}
		});
	}

	private static Object invokeMethod(Method method, Object object, Object[] args) {
		try {
			return method.invoke(object, args);
		} catch (Exception e) {
			throw new RedDeerException("Exception when invoking method " + method + " by reflection", e);
		}
	}
}
