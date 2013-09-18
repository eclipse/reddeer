package org.jboss.reddeer.swt.impl.clabel;

import org.jboss.reddeer.swt.api.CLabel;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Abstract class for all CLabel implementations
 * @author Jiri Peterka
 *
 */
public class AbstractCLabel implements CLabel {

	@Override
	public String getText() {
		return Bot.get().clabel().getText();
	}

}
