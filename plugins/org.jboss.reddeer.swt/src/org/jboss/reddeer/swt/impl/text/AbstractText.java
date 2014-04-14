package org.jboss.reddeer.swt.impl.text;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.handler.TextHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;

/**
 * Abstract class for all Text implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractText implements Text {
	
	protected org.eclipse.swt.widgets.Text w;
	protected final Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void setText(String str) {
		log.info("Text set to: " + str);
		TextHandler.getInstance().setText(w, str);
	}
	
	
	@Override
	public String getText() {
		String text = WidgetHandler.getInstance().getText(w);
		return text;
	}
	
	
	@Override
	public String getToolTipText() {
		String tooltipText = WidgetHandler.getInstance().getToolTipText(w);
		return tooltipText;
	}

	@Override
	public void setFocus() {
		log.info("Setting focus to Text");
		WidgetHandler.getInstance().setFocus(w);
	}
	
	public org.eclipse.swt.widgets.Text getSWTWidget(){
		return w;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(w);
	}
	
	@Override
	public void typeText(String text) {
		setText("");
		setFocus();
		KeyboardFactory.getKeyboard().type(text);
		
	}
	
	@Override
	public boolean isReadOnly(){
		return TextHandler.getInstance().isReadOnly(w);
	}
}
