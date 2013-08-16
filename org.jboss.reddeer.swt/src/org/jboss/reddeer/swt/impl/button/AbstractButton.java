package org.jboss.reddeer.swt.impl.button;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;
import org.jboss.reddeer.swt.matcher.ButtonLookup;
import org.jboss.reddeer.swt.matcher.StyleMatcher;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatcher;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Basic Button class is abstract class for all Button implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractButton implements Button {

	protected final Logger log = Logger.getLogger(this.getClass());

	protected org.eclipse.swt.widgets.Button swtButton;
	
	protected AbstractButton (int index , String text, int style){
		log.info("Searching for Button:"
				+ "\n  index: " + index
				+ "\n  label: " + text
				+ "\n  style: " + style);
			if (text != null && !text.isEmpty()) {
				swtButton = ButtonLookup.getInstance().getButton(
						index, new WithMnemonicMatcher(text), new StyleMatcher(style));
			} else {
				swtButton = ButtonLookup.getInstance().getButton(
						index, new StyleMatcher(style));
			}
	}
	@Override
	public void click() {
		log.info("Click on the button "
				+ (getText() != null ? getText() : (
						getToolTipText() != null ? getToolTipText()
						: "with no text or tooltip")));
		waitUntilButtonIsActive();
		WidgetHandler.getInstance().click(swtButton);
	}
	/**
	 * See {@link Button}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtButton);
	}
	/**
	 * See {@link Button}
	 */
	@Override
	public boolean isEnabled() {
		// TODO waits need to completely rewritten
		try {
			waitUntilButtonIsActive();
		} catch (WaitTimeoutExpiredException wtee) {
		}

		return WidgetLookup.getInstance().isEnabled(swtButton);
	}
	/**
	 * See {@link Button}
	 */
	@Override
	public String getToolTipText() {
		return WidgetHandler.getInstance().getToolTipText(swtButton);
	}

	private void waitUntilButtonIsActive() {

		new WaitUntil(new WaitCondition() {

			@Override
			public boolean test() {
				return WidgetLookup.getInstance().isEnabled(swtButton);
			}

			@Override
			public String description() {
				return "Button '" + getText() + "' was not enabled";
			}
		});

	}
}
