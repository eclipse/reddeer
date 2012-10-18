package org.jboss.reddeer.swt.exception;

/**
 * Thrower that throws exceptions when condition is fullfiled
 * @author Jiri Peterka
 *
 */
public class Thrower {

	/**
	 * Throw SWTLayerException with give message if object isnull
	 * @param object
	 * @param message
	 */
	public static void objectIsNull(Object object,String message) {
		if (object == null) {
			throw new SWTLayerException(message);
		}
	}
	
	public static void typeIsWrong(Object o,Class<?> c) {
			if (!o.getClass().isInstance(o))
				throw new SWTLayerException("Unexpected type, expected " + c.getName() + " actual " + o.getClass().getName());			
		
	}
}
