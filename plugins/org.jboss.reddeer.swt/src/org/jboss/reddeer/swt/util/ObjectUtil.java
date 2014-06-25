package org.jboss.reddeer.swt.util;

import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.exception.SWTLayerException;


/**
 * Object util contains helper methods for invoking methods by reflections, etc.
 * 
 * @author Jiri Peterka
 * 
 */
public class ObjectUtil {

	private static final Logger log = Logger.getLogger(ObjectUtil.class);
	
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
			throw new SWTLayerException("Exception when retrieving method " + methodName + " by reflection", e);
		}
		return method;
	}
	
	private static Object invokeMethodUI(final Method method, final Object object, final Object[] args) {
		InvokeMethodRunnable runnable = new InvokeMethodRunnable(method, object, args);
		
		Display.syncExec(runnable);

		if (runnable.exceptionOccurred()){
			log.error("Exception when invoking method " + method + " by reflection in UI thread", runnable.getException());
			throw new SWTLayerException("Exception when invoking method " + method + " by reflection in UI thread", runnable.getException());
		} else {
			return runnable.getResult();
		}
	}

	private static Object invokeMethod(Method method, Object object, Object[] args) {
		try {
			return method.invoke(object, args);
		} catch (Exception e) {
			throw new SWTLayerException("Exception when invoking method " + method + " by reflection", e);
		}
	}
	
	private static class InvokeMethodRunnable implements Runnable {
		
		private Method method;
		
		private Object object;
		
		private Object[] args;

		private Exception exception;
		
		private Object result = null;
		
		private InvokeMethodRunnable(Method method, Object object, Object[] args) {
			super();
			this.method = method;
			this.object = object;
			this.args = args;
		}

		@Override
		public void run() {
			try {
				result = invokeMethod(method, object, args);
			} catch (Exception e) {
				exception = e;
			}
		}
		
		public boolean exceptionOccurred(){
			return getException() != null;
		}
		
		public Exception getException() {
			return exception;
		}
		
		public Object getResult() {
			return result;
		}
	}
}
