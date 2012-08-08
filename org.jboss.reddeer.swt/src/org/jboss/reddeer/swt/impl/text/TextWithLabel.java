package org.jboss.reddeer.swt.impl.text;

import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Text with label implementation
 * @author Jiri Peterka
 *
 */
public class TextWithLabel extends AbstractText implements Text {

	public TextWithLabel(String label) {
		text = Bot.get().textWithLabel(label);
	}
}
