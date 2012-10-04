package org.jboss.reddeer.swt.impl.label;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.util.Bot;

public class DefaultLabel extends AbstractLabel {

	public DefaultLabel() {
		try {
			label = Bot.get().label();
		} catch (WidgetNotFoundException e){
			throw new WidgetNotAvailableException("No label found");
		}
	}

	public DefaultLabel(String text) {
		try {
			label = Bot.get().label(text);
		} catch (WidgetNotFoundException e){
			throw new WidgetNotAvailableException("No label with text '" + text + "' found");
		}
	}
}
