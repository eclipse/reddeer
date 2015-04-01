package org.jboss.reddeer.core.util;

import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.core.exception.CoreLayerException;

/**
 * Object util contains helper methods for invoking methods by reflections, etc.
 * 
 * @author Jiri Peterka
 */
public class ObjectUtil {

	/**
	 * Invokes method by reflection, widget based method are executed in ui thread
	 * 
	 * @param object given instance
	 * @param methodName method name to be invoked
	 * @return returns result of the method invocation
	 */
	public static Object invokeMethod(final Object object, String methodName) {
		return invokeMethod(object, methodName, new Class<?>[0], new Object[0]);
	}
	
	/**
	 * Invokes method by reflection, widget based method are executed in ui thread
	 * 
	 * @param object given instance
	 * @param methodName method name to be invoked
	 * @return returns result of the method invocation
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
			throw new CoreLayerException("Exception when retrieving method " + methodName + " by reflection", e);
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
			throw new CoreLayerException("Exception when invoking method " + method + " by reflection", e);
		}
	}
}
