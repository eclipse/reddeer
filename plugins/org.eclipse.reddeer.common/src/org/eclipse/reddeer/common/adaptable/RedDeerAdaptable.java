/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.common.adaptable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.reddeer.common.exception.AdaptableException;

/**
 * RedDeerAdaptable provides a way to get instances of adaptable objects. Such
 * object could be a more specific Default object or some default implementation
 * of a object in RedDeer abstraction.
 * 
 * @author mlabuda@redhat.com
 *
 * @param <K>
 *            generic type of top level adaptable object (interface) or abstract
 *            class
 */
public interface RedDeerAdaptable<K> {

	/**
	 * Gets adapted object of a specific class
	 * 
	 * @param <V> class extending top level adaptable object
	 * @param clazz
	 *            class of a adapted object
	 * @return adapted object
	 */
	default public <V extends K> V getAdapter(Class<V> clazz) {
		if (clazz == null) {
			throw new AdaptableException("Class provided to get adapted object cannot be null."); 
		}
	
		Object[] constructorArguments = getAdapterConstructorArguments();
		Constructor<V> constructor = null;
		
		try {
			if (constructorArguments == null | constructorArguments.length == 0) {
				for (Constructor constr: clazz.getConstructors()) {
					if (constr.getParameterCount() == 0) {
						constructor = constr;
						break;
					}
				}
			} else {
				constructor = clazz.getConstructor(getAdapterConstructorClasses());
			} 
		} catch (NoSuchMethodException | SecurityException e) {
			throw new AdaptableException("There is no suitable constructor to get adapted object. Check whether "
					+ "getAdapterConstructorArguments methods is properly implemented and class has proper "
					+ "constructor(s)");
		}
		
		if (constructor == null) {
			throw new AdaptableException("There is no suitable constructor to get adapted object. Check whether "
					+ "getAdapterConstructorArguments methods is properly implemented and class has proper "
					+ "constructor(s)");
		}
		
		V adapter = null;
		try {
			adapter = constructor.newInstance(constructorArguments);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// Should not happen, but if happens, throw an adaptable exception
			throw new AdaptableException("Could not create a new adapted object. ", e.getCause());
		} catch (InstantiationException ex) {
			throw new AdaptableException("Could not instantiate a new adapted object. Be sure you are "
					+ "overriding @{link RedDeerAdaptable.getAdapterConstructorArguments} method correctly,"
					+ " if your adapted instance have different constructor. And make sure that instance can"
					+ " be created by those arguments (constructor check on arguments pass)" + ex.getCause());
		}
		
		return adapter;
	}

	/**
	 * Gets all arguments used to construct an adapted object. Arguments must be
	 * in order and match a set of arguments used in a constructor of adaptable
	 * object.
	 * 
	 * @return array of constructor arguments of adaptable objects suitable for passing to adapted object
	 */
	public Object[] getAdapterConstructorArguments();
	
	/**
	 * Gets classes of all arguments used to construct an adapted object. Classes must be
	 * in order and match a set of classes of arguments used in a constructor of adaptable
	 * object &lt;K&gt;.
	 * 
	 * @return array of constructor classes of arguments of adaptable objects suitable for passing to adapted object
	 */
	public Class<?>[] getAdapterConstructorClasses();
}
