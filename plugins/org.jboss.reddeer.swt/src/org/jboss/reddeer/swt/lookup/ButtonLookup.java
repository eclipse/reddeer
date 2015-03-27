package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.widgets.Button;
import org.hamcrest.Matcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Button lookup containing lookup routines for Button widget type
 * @author jjankovi
 * @deprecated Since 1.0.0 use {@link AbstractWidget}
 *
 */
public class ButtonLookup {

	private static ButtonLookup instance = null;

	private ButtonLookup() {
	}

	/**
	 * Creates and returns instance of Button Lookup
	 * 
	 * @return ButtonLookup instance
	 */
	public static ButtonLookup getInstance() {
		if (instance == null)
			instance = new ButtonLookup();
		return instance;
	}

	/**
	 * Return Button instance
	 * 
	 * @param matcher
	 * @return Button Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public Button getButton(ReferencedComposite refComposite, int index, Matcher... matchers) {
		return (Button)WidgetLookup.getInstance().activeWidget(refComposite, Button.class, index, matchers);
	}
	
}