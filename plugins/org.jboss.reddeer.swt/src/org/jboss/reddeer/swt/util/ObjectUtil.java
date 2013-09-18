package org.jboss.reddeer.swt.util;

import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Widget;

/**
 * Object util contains helper methods for invoking methods by reflections, etc.
 * 
 * @author Jiri Peterka
 * 
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

		final Method method;
		try {
			method = object.getClass().getMethod(methodName, new Class[0]);
		} catch (Exception e) {
			throw new RuntimeException("Cannot get method : " + e.getMessage());
		}
		final Object result;
		if (object instanceof Widget) {

			result = Display.syncExec(new ResultRunnable<Object>() {

				@Override
				public Object run() {
					Object o = null;
					try {
						o = method.invoke(object, new Object[0]);
					} catch (Exception e) {
						try {
							throw new RuntimeException(
									"Cannot invoke method : " + e.getMessage());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					return o;
				}
			});
		} else
			try {
				result = method.invoke(object, new Object[0]);
			} catch (Exception e) {
				throw new RuntimeException("Cannot invoke method : "
						+ e.getMessage());
			}

		return result;
	}

}
