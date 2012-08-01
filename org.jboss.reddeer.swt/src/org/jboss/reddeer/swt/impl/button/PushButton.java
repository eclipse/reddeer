package org.jboss.reddeer.swt.impl.button;

import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.util.Bot;

/**
 * PushButton is simple button implementation that can be pushed
 * @author Jiri Peterka
 *
 */
public class PushButton extends BasicButton implements Button {

	public PushButton(String text) {
		button = Bot.get().button(text);
	}
}
