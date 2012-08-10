package org.jboss.reddeer.swt.impl.text;

import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Text with label implementation
 * @author Jiri Peterka
 *
 */
public class DefaultText extends AbstractText implements Text {

	/**
	 * Default text given by it's text
	 * @param label
	 */
	public DefaultText(String text) {
		botText = Bot.get().text(text);
	}
	
}
