package org.jboss.reddeer.generator;

import org.eclipse.swt.widgets.Control;

/**
 * Interface for all code generators. If the code generator supports a given control/widget then it will generate the
 * appropriate code (it will generate constants, getter/setter methods, etc).
 * 
 * @author apodhrad
 *
 */
public interface CodeGenerator {

	/**
	 * Returns whether a given control/widget is supported.
	 * 
	 * @param control
	 *            control/widget
	 * @return whether a given control/widget is supported
	 */
	boolean isSupported(Control control);

	/**
	 * Returns lookup constructor for a given control/widget
	 * 
	 * @param control
	 *            control/widget
	 * @return lookup constructor for a given control/widget
	 */
	String getConstructor(Control control);

	/**
	 * Return the appropriate code (constants, getter/setter methods, etc).
	 * 
	 * @param control
	 *            control/widget
	 * @return generated code
	 */
	String getGeneratedCode(Control control);
}
