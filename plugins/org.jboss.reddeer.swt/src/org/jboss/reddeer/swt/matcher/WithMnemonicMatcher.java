package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * With Mnemonic matcher. Should be used for menu item label matching.
 * Removes all ampersands and shortcuts from input text before comparing
 * 
 * @author Vlado Pakan
 * 
 * @deprecated in 0.5, use {@link WithMnemonicTextMatcher}
 * 
 */
public class WithMnemonicMatcher extends BaseMatcher<String> {

	private String text;
	
	public WithMnemonicMatcher(String text) {
		this.text = text;
	}
	
	@Override
	public void describeTo(Description description) {

	}

	/**
	 * Text matches
	 */
	@Override
	public boolean matches(Object item) {
		
		if (item instanceof String) {
			String textToMatch = ((String)item).replaceAll("&", "").split("\t")[0];
			if (textToMatch.equals(text)) {
				return true;
			}
			
		} else  if (item instanceof Widget){
			try {
				String widgetText = WidgetHandler.getInstance().getText((Widget)item);
				if(widgetText != null){
					String textToMatch = widgetText.replaceAll("&", "").split("\t")[0];
					if (textToMatch.equals(text))
						return true;
					}
			} catch (SWTLayerException sle) {
				// object is not supported by widget handler mechanism 'getText' 
			}
		}
		return false;
	}
}
