package org.jboss.reddeer.swt.exception;

/**
 * Thrower throws exceptions when condition is met.
 * 
 * @author Jiri Peterka
 *
 */
public class Thrower {

	/**
	 * Throws SWTLayerException with specified message if specified object is null.
	 * 
	 * @param object object to check its existence
	 * @param message message to throw if object is null
	 */
	public static void objectIsNull(Object object, String message) {
		if (object == null) {
			throw new SWTLayerException(message);
		}
	}

	/**
	 * Throws SWTLayerException if specified object is not instance of specified class.
	 * 
	 * @param object object to check its type
	 * @param clazz matching class
	 */
	public static void typeIsWrong(Object object, Class<?> clazz) {
		if (!object.getClass().isInstance(object)) {
			throw new SWTLayerException("Unexpected type, expected "
					+ clazz.getName() + " actual " + object.getClass().getName());
		}
	}
}
