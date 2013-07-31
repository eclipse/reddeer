package org.jboss.reddeer.swt.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Widget style matcher
 * @author jjankovi
 *
 */
public class StyleMatcher extends BaseMatcher<Integer>{

	private int style; 
	
	public StyleMatcher(int style) {
		this.style = style;
	}
	
	@Override
	public boolean matches(Object item) {
		if (item instanceof Integer) {
			Integer integerToMatch = (Integer)item;
			return integerToMatch.compareTo(style) == 0;
		} else {
			try {
				Integer widgetStyle = WidgetHandler.getInstance().getStyle(item);
				return (widgetStyle.intValue() & style) != 0;
			} catch (SWTLayerException sle) {
				// object is not supported by widget handler mechanism 'getStyle'
				return false;
			}
		}
	}

	@Override
	public void describeTo(Description arg0) {
		// TODO Auto-generated method stub
	}

}
