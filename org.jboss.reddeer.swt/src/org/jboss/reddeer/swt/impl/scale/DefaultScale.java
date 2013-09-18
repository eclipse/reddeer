package org.jboss.reddeer.swt.impl.scale;

import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;
/**
 * DefaultScale implementation represents most common Scale widget type
 * and provide API for basic operation needed in UI tests
 * @author Vlado Pakan
 *
 */
public class DefaultScale extends AbstractScale {

	/**
	 * Create DefautScale instance based on first available Scale found
	 */
	public DefaultScale() {
		this(0);
	}
	/**
	 * Create DefaultScale instance matching given index
	 * @param index
	 */
	public DefaultScale(int index) {
		super(WidgetLookup.getInstance().activeWidget(org.eclipse.swt.widgets.Scale.class,index));
	}
	
}
