package org.jboss.reddeer.swt.impl.scale;

import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
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
	 * Create DefautScale instance based on first available Scale found inside given composite
	 * @param referencedComposite
	 */
	public DefaultScale(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Create DefaultScale instance matching given index
	 * @param index
	 */
	public DefaultScale(int index) {
		super(WidgetLookup.getInstance().activeWidget(null, org.eclipse.swt.widgets.Scale.class,index));
	}
	
	/**
	 * Create DefaultScale instance matching given index inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultScale(ReferencedComposite referencedComposite, int index) {
		super(WidgetLookup.getInstance().activeWidget(referencedComposite, org.eclipse.swt.widgets.Scale.class,index));
	}
	
}
