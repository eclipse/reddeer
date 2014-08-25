package org.jboss.reddeer.swt.impl.text;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.handler.TextHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all Text implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractText extends AbstractWidget<org.eclipse.swt.widgets.Text> implements Text {
	
	private static final Logger log = Logger.getLogger(AbstractText.class);

	protected AbstractText(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.Text.class, refComposite, index, matchers);
	}
	
	@Override
	public void setText(String str) {
		log.info("Text set to: " + str);
		TextHandler.getInstance().setText(swtWidget, str);
	}
	
	@Override
	public String getText() {
		String text = WidgetHandler.getInstance().getText(swtWidget);
		return text;
	}
	
	@Override
	public String getMessage() {
		return TextHandler.getInstance().getMessage(swtWidget);
	}
	
	@Override
	public String getToolTipText() {
		String tooltipText = WidgetHandler.getInstance().getToolTipText(swtWidget);
		return tooltipText;
	}

	@Override
	public void setFocus() {
		log.debug("Set focus to Text");
		WidgetHandler.getInstance().setFocus(swtWidget);
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtWidget);
	}
	
	@Override
	public void typeText(String text) {
		log.info("Type text " + text);
		setText("");
		setFocus();
		KeyboardFactory.getKeyboard().type(text);
		
	}
	
	@Override
	public boolean isReadOnly(){
		return TextHandler.getInstance().isReadOnly(swtWidget);
	}
}
