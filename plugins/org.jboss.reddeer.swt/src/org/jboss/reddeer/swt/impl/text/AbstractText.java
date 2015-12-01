package org.jboss.reddeer.swt.impl.text;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.core.handler.TextHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.core.reference.ReferencedComposite;
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
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Text#setText(java.lang.String)
	 */
	@Override
	public void setText(String str) {
		log.info("Text set to: " + str);
		TextHandler.getInstance().setText(swtWidget, str);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Text#getText()
	 */
	@Override
	public String getText() {
		String text = WidgetHandler.getInstance().getText(swtWidget);
		return text;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Text#getMessage()
	 */
	@Override
	public String getMessage() {
		return TextHandler.getInstance().getMessage(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Text#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		String tooltipText = WidgetHandler.getInstance().getToolTipText(swtWidget);
		return tooltipText;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Text#setFocus()
	 */
	@Override
	public void setFocus() {
		log.debug("Set focus to Text");
		WidgetHandler.getInstance().setFocus(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.widgets.AbstractWidget#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Text#typeText(java.lang.String)
	 */
	@Override
	public void typeText(String text) {
		log.info("Type text " + text);
		setText("");
		setFocus();
		forceFocus();
		KeyboardFactory.getKeyboard().type(text);		
	}
	
	private void forceFocus() {
		log.debug("Force focus to receive keyboard events");
		WidgetHandler.getInstance().forceFocus(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Text#isReadOnly()
	 */
	@Override
	public boolean isReadOnly(){
		return TextHandler.getInstance().isReadOnly(swtWidget);
	}
}
