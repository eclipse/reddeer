package org.jboss.reddeer.swt.impl.button;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Basic Button class is abstract class for all Button implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractButton implements Button {

	protected final Logger log = Logger.getLogger(this.getClass());

	protected SWTBotButton button;

	@Override
	public void click() {
		log.info("Click on the button "
				+ (button.getText() != null ? button.getText() : (button
						.getToolTipText() != null ? button.getToolTipText()
						: "with no text or tooltip")));
		waitUntilButtonIsActive();
		button.click();
	}

	@Override
	public String getText() {
		return button.getText();
	}

	@Override
	public boolean isEnabled() {
		// TODO waits need to completely rewritten
		try {
			waitUntilButtonIsActive();
		} catch (TimeoutException e) {
		}

		return button.isEnabled();
	}

	private void waitUntilButtonIsActive() {

		new WaitUntil(new WaitCondition() {

			@Override
			public boolean test() {
				return Display.syncExec(new ResultRunnable<Boolean>() {

					@Override
					public Boolean run() {
						return ((org.eclipse.swt.widgets.Button) button.widget)
								.isEnabled();
					}
				});
			}

			@Override
			public String description() {
				return "Button '" + getText() + "' was not enabled";
			}
		});

	}
}
